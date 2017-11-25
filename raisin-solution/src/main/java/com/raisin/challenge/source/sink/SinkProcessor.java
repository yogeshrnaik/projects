package com.raisin.challenge.source.sink;

import static com.raisin.challenge.util.ThreadUtil.notifyOthers;
import static com.raisin.challenge.util.Util.is406Error;

import org.apache.log4j.Logger;

import com.raisin.challenge.source.message.MessageDto;
import com.raisin.challenge.source.message.MessageQueue;

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
        notifyOthers(lock);
        LOGGER.info("Sink processing finished.");
    }

    private void processUntilNotAllSourcesDone() {
        while (sinkData.notAllDataProcessed()) {
            MessageDto msg = takeFromQueue();
            try {
                process(msg);
            } catch (Throwable t) {
                LOGGER.warn(String.format("Error occurred while processing message: [%s]", msg), t);
                if (is406Error(t)) {
                    notifyOthers(lock); // but don't wait as we will wait on the queue anyways
                }
                if (isConnectionClosed(t))
                    return;
            }
        }
    }

    private MessageDto takeFromQueue() {
        MessageDto msg = msgQueue.next();
        LOGGER.info("Message taken from queue: " + msg);
        return msg;
    }

    private void process(MessageDto msg) {
        if (msg.isDone()) {
            sinkData.markSourceDone(msg.getSource());
            processOrphanRecords(msg);
        } else {
            processIdMessage(msg);
        }
    }

    private void processIdMessage(MessageDto msg) {
        // see if there is any match available, if yes, send "joined" else add to source data
        if (sinkData.isJoined(msg)) {
            processJoined(msg);
        } else if (sinkData.isAnySourceDone()) {
            processAnySourceDone(msg);
        } else {
            // no match found, so add to source data
            sinkData.addToSourceData(msg);
        }
    }

    private void processAnySourceDone(MessageDto msg) {
        // when any of the source is already done, then all incoming records are orphans
        sinkData.addToSourceData(msg);

        String doneSource = sinkData.getDoneSource();
        if (doneSource != null) {
            processOrphanRecords(new MessageDto(doneSource, true));
        }
    }

    private void processJoined(MessageDto msg) {
        // send "joined" and remove all records from source data for this id
        sinkWriter.write(msg.getId(), "joined");
        sinkData.removeFromSourceData(msg);
    }

    private void processOrphanRecords(MessageDto msg) {
        // process orphan records till we get error
        while (true) {
            MessageDto orphan = sinkData.getOrphanRecord(msg.getSource());
            if (orphan == null) {
                LOGGER.info("There are no orphans.");
                return;
            }
            processOrphan(orphan);
        }
    }

    private void processOrphan(MessageDto orphan) {
        LOGGER.info("Processing orphan: " + orphan);
        sinkWriter.write(orphan.getId(), "orphaned");
        sinkData.removeFromSourceData(orphan);
    }

    private boolean isConnectionClosed(Throwable t) {
        return (t != null && t.toString().contains("Connection refused"));
    }
}
