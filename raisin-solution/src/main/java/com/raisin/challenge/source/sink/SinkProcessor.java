package com.raisin.challenge.source.sink;

import static com.raisin.challenge.util.ThreadUtil.notifyOthers;

import org.apache.log4j.Logger;

import com.raisin.challenge.exception.NotAcceptableException;
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
            if (!processMessage(takeFromQueue())) {
                return;
            }
        }
    }

    private boolean processMessage(MessageDto msg) {
        try {
            process(msg);
        } catch (NotAcceptableException e) {
            LOGGER.warn(String.format("Error occurred while processing message: [%s]. Error: [%s]", msg, e));
            notifyOthers(lock); // but don't wait as we will wait on the queue anyways
        } catch (Throwable t) {
            LOGGER.error(String.format("Error occurred while processing message: [%s]", msg), t);
            if (isConnectionClosed(t))
                return false;
        }
        return true;
    }

    private MessageDto takeFromQueue() {
        MessageDto msg = msgQueue.next();
        LOGGER.info("Message taken from queue: " + msg);
        return msg;
    }

    private void process(MessageDto msg) {
        if (msg.isDone()) {
            processDoneMessage(msg);
        } else {
            processIdMessage(msg);
        }
    }

    private void processDoneMessage(MessageDto msg) {
        sinkData.markSourceDone(msg.getSource());
        processRecordsForDoneSource(msg.getSource());
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
            processRecordsForDoneSource(doneSource);
        }
    }

    private void processJoined(MessageDto msg) {
        // send "joined" and remove all records from sink data for this id
        sinkData.addToSourceData(msg);
        sinkWriter.write(msg.getId(), "joined");
        sinkData.removeFromSourceData(msg);
    }

    private void processRecordsForDoneSource(String doneSource) {
        // process existing joined records and orphan records
        processJoinedRecords(doneSource);
        processOrphanRecords(doneSource);
    }

    private void processOrphanRecords(String doneSource) {
        // process existing orphan records till we get error
        while (true) {
            MessageDto orphan = sinkData.getOrphanRecord(doneSource);
            if (orphan == null) {
                LOGGER.info("There are no orphan records");
                break;
            }
            processRecord(orphan, "orphaned");
        }
    }

    private void processJoinedRecords(String doneSource) {
        // process existing joined records till we get error
        while (true) {
            MessageDto joined = sinkData.getJoinedRecord(doneSource);
            if (joined == null) {
                LOGGER.info("There are no joined records.");
                break;
            }
            processRecord(joined, "joined");
        }
    }

    private void processRecord(MessageDto record, String type) {
        LOGGER.info(String.format("Processing [%s] record [%s]...", type, record));
        sinkWriter.write(record.getId(), type);
        sinkData.removeFromSourceData(record);
    }

    private boolean isConnectionClosed(Throwable t) {
        return (t != null && t.toString().contains("Connection refused"));
    }
}
