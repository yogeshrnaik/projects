package com.raisin.challenge.source.sink;

import static java.lang.Boolean.parseBoolean;
import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.raisin.challenge.source.message.MessageDto;

public class SinkData {

    private final Map<String, Boolean> sourceDoneFlags;
    private final Map<String, List<String>> sourceData;

    public SinkData(String... sources) {
        this.sourceDoneFlags = getSourceFlagsMap(sources);
        this.sourceData = initSourceDataMap(sources);
    }

    public Collection<List<String>> getSourceData() {
        return sourceData.values();
    }

    private Map<String, List<String>> initSourceDataMap(String[] sources) {
        return new ConcurrentHashMap<>(Arrays.asList(sources).stream().map(it -> new Object[] {it, it})
            .collect(toMap(e -> e[0].toString(), e -> Lists.<String>newArrayList())));
    }

    private Map<String, Boolean> getSourceFlagsMap(String... sources) {
        return new ConcurrentHashMap<>(Arrays.asList(sources).stream().map(it -> new Object[] {it, false})
            .collect(toMap(e -> e[0].toString(), e -> parseBoolean(e[1].toString()))));
    }

    public boolean allDataProcessed() {
        return !notAllSourcesDone() && sourceData.values().stream().allMatch(e -> e.size() == 0);
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

    public void setSourceDone(String source) {
        sourceDoneFlags.put(source, true);
    }

    public void addToSourceData(MessageDto msg) {
        sourceData.get(msg.getSource()).add(msg.getId());
    }

    public void removeFromSourceData(MessageDto msg) {
        sourceData.entrySet().stream()
            .forEach(it -> it.getValue().remove(msg.getId()));
    }

    public boolean isJoined(MessageDto msg) {
        return sourceData.entrySet().stream().filter(it -> !it.getKey().equals(msg.getSource()))
            .allMatch(it -> it.getValue().contains(msg.getId()));
    }

    public void clearSourceData() {
        sourceData.clear();
    }

    public MessageDto getOrphanRecord(String source) {
        // get record belonging to another source for which there is no record in this source
        String otherSource = sourceData.keySet().stream().filter(e -> !e.equals(source)).findFirst().get();

        for (String id : sourceData.get(otherSource)) {
            if (!sourceData.get(source).contains(id))
                return new MessageDto(otherSource, id);
        }
        return null;
    }

    public List<MessageDto> getOrphanRecords(String source) {
        // get records belonging to another source for which there is no record in this source
        String otherSource = sourceData.keySet().stream().filter(e -> !e.equals(source)).findFirst().get();

        return sourceData.get(otherSource).stream()
            .filter(id -> !sourceData.get(source).contains(id))
            .map(id -> new MessageDto(otherSource, id))
            .collect(Collectors.toList());
    }
}
