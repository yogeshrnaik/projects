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
    private final Object lock;

    public SinkProcessor(int sinkId, String sinkUrl, MessageQueue msgQueue, SinkData sinkData, Object lock) {
        this.sinkId = sinkId;
        this.sinkWriter = new SinkWriter(sinkUrl);
        this.msgQueue = msgQueue;
        this.sinkData = sinkData;
        this.lock = lock;
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
            LOGGER.info("Message taken from queue: " + msg);
            try {
                processMessage(msg);
            } catch (Throwable t) {
                LOGGER.warn(String.format("Error occurred while processing message: [%s]", msg), t);
                if (Util.is406Error(t)) {
                    notifyOthers(); // but dont wait as we will wait on the queue anyways
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
            sinkWriter.write(msg.getId(), "joined");
            sinkData.removeFromSourceData(msg);
        } else if (sinkData.isAnySourceDone()) {
            // when any of the source is already done, then all new records are to be marked as orphan
            sinkData.addToSourceData(msg);
            // sinkWriter.write(msg.getId(), "orphaned");
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
        // process few orphan records
        while (true) {
            MessageDto orphan = sinkData.getOrphanRecord(msg.getSource());
            if (orphan == null) {
                LOGGER.info("There are no orphans.");
                return;
            }

            LOGGER.info("Processing orphan: " + orphan);
            sinkWriter.write(orphan.getId(), "orphaned");
            sinkData.removeFromSourceData(orphan);
        }
    }

    private boolean isConnectionClosed(Throwable t) {
        return (t != null && t.toString().contains("Connection refused"));
    }

    private void notifyOthers() {
        synchronized (lock) {
            LOGGER.info("Notifying others...");
            lock.notifyAll();
        }
    }
}
