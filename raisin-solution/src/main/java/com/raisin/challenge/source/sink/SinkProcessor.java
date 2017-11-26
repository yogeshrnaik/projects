package com.raisin.challenge.source.sink;

import static com.raisin.challenge.util.ThreadUtil.notifyOthers;
import static com.raisin.challenge.util.Util.isConnectionClosed;

import org.apache.log4j.Logger;

import com.raisin.challenge.exception.NotAcceptableException;
import com.raisin.challenge.source.message.SourceMessage;
import com.raisin.challenge.source.message.SourceMessageQueue;

/**
 * Reads message from source queue and sinks data to sink URL.<br/>
 * On receiving DONE message from any source, sinks all the joined and orphaned records till it gets 406. <br/>
 * In case of 406 error, notifies others and waits till there is any new message in the source queue.
 */
public class SinkProcessor implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(SinkProcessor.class);

    private final SinkData sinkData;
    private final SourceMessageQueue sourceMsgQueue;
    private final SinkWriter sinkWriter;
    private final Object lock;

    public SinkProcessor(String sinkUrl, SourceMessageQueue msgQueue, SinkData sinkData, Object lock) {
        this.sinkWriter = new SinkWriter(sinkUrl);
        this.sourceMsgQueue = msgQueue;
        this.sinkData = sinkData;
        this.lock = lock;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("SinkProcessor");
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

    /**
     * Processes source message.
     * 
     * @return Returns true by default indicating to continue processing next message. <br/>
     *         Returns false if connection to sink is closed.
     */
    private boolean processMessage(SourceMessage msg) {
        try {
            process(msg);
        } catch (NotAcceptableException e) {
            LOGGER.warn(String.format("Error occurred while processing message: [%s]. Error: [%s]", msg, e));
            notifyOthers(lock); // but don't wait as we will wait on the source queue anyways
        } catch (Throwable t) {
            LOGGER.error(String.format("Error occurred while processing message: [%s]", msg), t);
            if (isConnectionClosed(t))
                return false;
        }
        return true;
    }

    private SourceMessage takeFromQueue() {
        return sourceMsgQueue.next();
    }

    private void process(SourceMessage msg) {
        if (msg.isDone()) {
            processDoneMessage(msg);
        } else {
            processIdMessage(msg);
        }
    }

    private void processDoneMessage(SourceMessage msg) {
        sinkData.markSourceDone(msg.getSource());
        processRecordsForDoneSource(msg.getSource());
    }

    private void processIdMessage(SourceMessage msg) {
        // add to sink data first
        sinkData.add(msg);

        if (sinkData.isJoined(msg)) {
            processRecord(msg, "joined");
        } else if (sinkData.isAnySourceDone()) {
            processAnySourceDone(msg);
        }
    }

    private void processAnySourceDone(SourceMessage msg) {
        // when any of the source is already done, then all incoming records are orphans
        String doneSource = sinkData.getDoneSource();
        if (doneSource != null) {
            processRecordsForDoneSource(doneSource);
        }
    }

    private void processRecordsForDoneSource(String doneSource) {
        // process existing joined records and orphan records
        processJoinedRecords(doneSource);
        processOrphanRecords(doneSource);
    }

    private void processOrphanRecords(String doneSource) {
        // process existing orphan records till we get error
        while (true) {
            SourceMessage orphan = sinkData.getOrphanRecord(doneSource);
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
            SourceMessage joined = sinkData.getJoinedRecord(doneSource);
            if (joined == null) {
                LOGGER.info("There are no joined records.");
                break;
            }
            processRecord(joined, "joined");
        }
    }

    private void processRecord(SourceMessage record, String type) {
        LOGGER.debug(String.format("Processing [%s] record [%s]...", type, record));
        sinkWriter.write(record.getId(), type);
        sinkData.remove(record);
    }

}
