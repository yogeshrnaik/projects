package com.raisin.challenge.source.sink;

import org.apache.log4j.Logger;

import com.raisin.challenge.source.message.MessageDto;
import com.raisin.challenge.source.message.MessageQueue;
import com.raisin.challenge.util.Util;

public class SinkProcessor implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(SinkProcessor.class);

    private final int sinkId;
    private final SinkWriter sinkWriter;
    private final SinkData sinkData;
    private final MessageQueue msgQueue;

    public SinkProcessor(int sinkId, String sinkUrl, MessageQueue msgQueue, SinkData sinkData) {
        this.sinkId = sinkId;
        this.sinkWriter = new SinkWriter(sinkUrl);
        this.msgQueue = msgQueue;
        this.sinkData = sinkData;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("SinkProcessor_" + sinkId);
        processUntilNotAllSourcesDone();
        notifyOthers();
        LOGGER.info("Sink processing finished.");
    }

    private void processUntilNotAllSourcesDone() {
        while (!sinkData.allDataProcessed()) {
            MessageDto msg = msgQueue.next();
            try {
                processMessage(msg);
            } catch (Throwable t) {
                LOGGER.warn(String.format("Error occurred while processing message: [%s]", msg), t);
                if (Util.is406Error(t)) {
                    notifyOthersAndWait();
                }
                if (isConnectionClosed(t))
                    return;
            }
        }
    }

    private void processMessage(MessageDto msg) {
        if (msg.isDone()) {
            // update flag
            sinkData.setSourceDone(msg.getSource());
            processOrphanRecords(msg);
            return;
        }

        // see if there is any match available, if yes, send "joined" else add to source data
        if (sinkData.isJoined(msg)) {
            // send "joined" and remove all records from source data for this id
            sinkData(msg, "joined");
        } else if (sinkData.isAnySourceDone()) {
            // when any of the source is already done, then all new records are to be marked as orphan
            sinkWriter.write(msg.getId(), "orphaned");
            String doneSource = sinkData.getDoneSource();
            if (doneSource != null) {
                processOrphanRecords(new MessageDto(doneSource, true));
            }
        } else {
            // no match found, so add to source data
            sinkData.addToSourceData(msg);
        }
    }

    private void processOrphanRecords(MessageDto msg) {
        while (true) {
            MessageDto orphan = sinkData.getOrphanRecord(msg.getSource());
            if (orphan == null)
                return;
            sinkData(orphan, "orphaned");
        }
    }

    private void sinkData(MessageDto msg, String type) {
        sinkWriter.write(msg.getId(), type);
        sinkData.removeFromSourceData(msg);
    }

    private boolean isConnectionClosed(Throwable t) {
        return (t != null && t.toString().contains("Connection refused"));
    }

    private void notifyOthersAndWait() {
        LOGGER.info("Notifying others and waiting...");
        notifyOthers();
        waitTillNotified();
    }

    private void waitTillNotified() {
        try {
            synchronized (sinkData) {
                LOGGER.info("Waiting...");
                sinkData.wait();
            }
        } catch (InterruptedException e) {
            LOGGER.warn("Interrupted when waiting...");
        }
    }

    private void notifyOthers() {
        synchronized (sinkData) {
            LOGGER.info("Notifying others...");
            sinkData.notifyAll();
        }
    }
}
