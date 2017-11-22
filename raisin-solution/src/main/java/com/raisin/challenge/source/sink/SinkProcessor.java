package com.raisin.challenge.source.sink;

import static java.lang.Boolean.parseBoolean;
import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;
import com.raisin.challenge.source.message.MessageDto;
import com.raisin.challenge.source.message.MessageQueue;

public class SinkProcessor implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(SinkProcessor.class);

    private final SinkWriter sinkWriter;
    private final Map<String, Boolean> sourceDoneFlags;
    private final Map<String, List<String>> sourceData;

    private final MessageQueue msgQueue;

    public SinkProcessor(String sinkUrl, MessageQueue msgQueue, String... sources) {
        this.sinkWriter = new SinkWriter(sinkUrl);
        this.sourceDoneFlags = getSourceFlagsMap(sources);
        this.sourceData = initSourceDataMap(sources);
        this.msgQueue = msgQueue;
    }

    private Map<String, List<String>> initSourceDataMap(String[] sources) {
        return Arrays.asList(sources).stream().map(it -> new Object[] {it, it})
            .collect(toMap(e -> e[0].toString(), e -> Lists.<String>newArrayList()));
    }

    private Map<String, Boolean> getSourceFlagsMap(String... sources) {
        return Arrays.asList(sources).stream().map(it -> new Object[] {it, false})
            .collect(toMap(e -> e[0].toString(), e -> parseBoolean(e[1].toString())));
    }

    @Override
    public void run() {
        Thread.currentThread().setName("SinkProcessor");
        processUntilNotAllSourcesDone();
        processRemainingOrphanRecords();
        LOGGER.info("Sink processing finished.");

    }

    private void processUntilNotAllSourcesDone() {
        while (notAllSourcesDone()) {
            MessageDto msg = msgQueue.next();
            processMessage(msg);
        }
    }

    private void processRemainingOrphanRecords() {
        LOGGER.info("Processing remainin orphan records...");
        sourceData.values().stream().forEach(it -> it.stream().forEach(id -> sinkWriter.write(id, "orphaned")));
        sourceData.clear();
    }

    private void processMessage(MessageDto msg) {
        if (msg.isDone()) {
            // update flag
            sourceDoneFlags.put(msg.getSource(), true);
            return;
        }

        // see if there is any match available, if yes, send "joined" else add to source data
        if (isJoined(msg)) {
            // send "joined" and remove all records from source data for this id
            sinkWriter.write(msg.getId(), "joined");
            removeFromSourceData(msg);
        } else if (isAnySourceDone()) {
            // when any of the source is already done, then all new records are to be marked as orphan
            sinkWriter.write(msg.getId(), "orphaned");
        } else {
            // no match found, so add to source data and wait
            sourceData.get(msg.getSource()).add(msg.getId());
        }
    }

    private void removeFromSourceData(MessageDto msg) {
        this.sourceData.entrySet().stream()
            .filter(e -> !e.getKey().equals(msg.getSource()))
            .forEach(it -> it.getValue().remove(msg.getId()));
    }

    private boolean notAllSourcesDone() {
        return sourceDoneFlags.values().stream().anyMatch(it -> it == false);
    }

    private boolean isAnySourceDone() {
        return sourceDoneFlags.values().stream().anyMatch(it -> it == true);
    }

    private boolean isJoined(MessageDto msg) {
        return sourceData.entrySet().stream().filter(it -> !it.getKey().equals(msg.getSource()))
            .allMatch(it -> it.getValue().contains(msg.getId()));
    }
}
