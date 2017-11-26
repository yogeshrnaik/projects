package com.raisin.challenge.source.sink;

import static java.lang.Boolean.parseBoolean;
import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.Lists;
import com.raisin.challenge.source.message.SourceMessage;

public class SinkData {

    private final int sourcesCount;
    private final Map<String, Boolean> sourceDoneFlags;
    private final Map<String, List<String>> idWiseMap;

    public SinkData(String... sources) {
        this.sourcesCount = sources.length;
        this.sourceDoneFlags = getSourceFlagsMap(sources);
        this.idWiseMap = new ConcurrentHashMap<>();
    }

    private Map<String, Boolean> getSourceFlagsMap(String... sources) {
        return new ConcurrentHashMap<>(Arrays.asList(sources).stream().map(it -> new Object[] {it, false})
            .collect(toMap(e -> e[0].toString(), e -> parseBoolean(e[1].toString()))));
    }

    public boolean notAllDataProcessed() {
        return !allDataProcessed();
    }

    public boolean allDataProcessed() {
        return !notAllSourcesDone() && idWiseMap.isEmpty();
    }

    public boolean notAllSourcesDone() {
        return sourceDoneFlags.values().stream().anyMatch(it -> it == false);
    }

    public boolean isAnySourceDone() {
        return sourceDoneFlags.values().stream().anyMatch(it -> it == true);
    }

    public String getDoneSource() {
        Optional<Entry<String, Boolean>> doneSource = sourceDoneFlags.entrySet().stream().filter(e -> e.getValue()).findFirst();
        return doneSource.isPresent() ? doneSource.get().getKey() : null;
    }

    public void markSourceDone(String source) {
        sourceDoneFlags.put(source, true);
    }

    public void add(SourceMessage msg) {
        List<String> sources = idWiseMap.get(msg.getId());
        if (sources == null) {
            sources = Lists.newArrayList();
        }
        sources.add(msg.getSource());
        idWiseMap.put(msg.getId(), sources);
    }

    public void remove(SourceMessage msg) {
        idWiseMap.remove(msg.getId());
    }

    public boolean isJoined(SourceMessage msg) {
        List<String> sources = idWiseMap.get(msg.getId());
        return (sources != null && sources.size() == sourcesCount);
    }

    public void clearSourceData() {
        idWiseMap.clear();
    }

    public SourceMessage getOrphanRecord(String doneSource) {
        Optional<SourceMessage> orphan = idWiseMap.entrySet().stream()
            .filter(e -> !e.getValue().contains(doneSource))
            .map(e -> new SourceMessage(e.getValue().get(0), e.getKey()))
            .findFirst();
        return orphan.isPresent() ? orphan.get() : null;
    }

    public SourceMessage getJoinedRecord(String source) {
        Optional<SourceMessage> joined = idWiseMap.entrySet().stream()
            .filter(e -> e.getValue().size() == sourcesCount)
            .map(e -> new SourceMessage(source, e.getKey())).findFirst();

        return joined.isPresent() ? joined.get() : null;
    }
}
