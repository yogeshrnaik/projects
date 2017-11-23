package com.raisin.challenge.source.sink;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.raisin.challenge.source.SourceReader;
import com.raisin.challenge.source.SourceResponse;
import com.raisin.challenge.source.message.MessageDto;
import com.raisin.challenge.source.message.MessageQueue;

public class SinkProcessor implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(SinkProcessor.class);

    private final int sinkId;
    private final SinkWriter sinkWriter;

    private SinkData sinkData;

    private final MessageQueue msgQueue;

    private final List<SourceReader> sourceReaders;

    public SinkProcessor(int sinkId, String sinkUrl, MessageQueue msgQueue, SinkData sinkData, SourceReader... srcReaders) {
        this.sinkId = sinkId;
        this.sinkWriter = new SinkWriter(sinkUrl);
        this.msgQueue = msgQueue;
        this.sinkData = sinkData;
        this.sourceReaders = Arrays.asList(srcReaders);
    }

    @Override
    public void run() {
        Thread.currentThread().setName("SinkProcessor_" + sinkId);
        processUntilNotAllSourcesDone();
        processRemainingOrphanRecords();
        LOGGER.info("Sink processing finished.");
    }

    private void processUntilNotAllSourcesDone() {
        while (sinkData.notAllSourcesDone()) {
            MessageDto msg = msgQueue.next();
            processMessage(msg);
        }
    }

    private void processRemainingOrphanRecords() {
        LOGGER.info("Processing remaining orphan records...");
        int index = 0;

        for (List<String> data : sinkData.getSourceData()) {
            for (String id : data) {
                while (!write(id, "orphaned")) {
                    // read from source
                    index = readFromSource(index);
                }
            }
        }
        sinkData.clearSourceData();
    }

    private boolean write(String id, String type) {
        try {
            sinkWriter.write(id, type);
            return true;
        } catch (Throwable t) {
            LOGGER.warn("Error while writing to sink: " + id + " : " + type);
            return !t.toString().contains("406 you gotta read somewhere else first");
        }
    }

    private int readFromSource(int index) {
        // send read request before sending any write request
        if (index == sourceReaders.size()) {
            index = 0;
        }
        try {
            SourceResponse msg = sourceReaders.get(index++).read();
            LOGGER.info(String.format("Read from source: [%s]", msg.getRawResponse()));
        } catch (Exception e) {
            LOGGER.warn("Ignoring error occurred while reading from source", e);
        }
        return index;
    }

    private void processMessage(MessageDto msg) {
        if (msg.isDone()) {
            // update flag
            sinkData.setSourceDone(msg.getSource());
            // process an orphan record
            MessageDto orphan = sinkData.getOrphanRecord(msg.getSource());
            sinkWriter.write(orphan.getId(), "orphaned");
            sinkData.removeFromSourceData(orphan);
            return;
        }

        // see if there is any match available, if yes, send "joined" else add to source data
        if (sinkData.isJoined(msg)) {
            // send "joined" and remove all records from source data for this id
            sinkWriter.write(msg.getId(), "joined");
            sinkData.removeFromSourceData(msg);
        } else if (sinkData.isAnySourceDone()) {
            // when any of the source is already done, then all new records are to be marked as orphan
            sinkWriter.write(msg.getId(), "orphaned");
        } else {
            // no match found, so add to source data and wait
            sinkData.addToSourceData(msg);
        }
    }
}
