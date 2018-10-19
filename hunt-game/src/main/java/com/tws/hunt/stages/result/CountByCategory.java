package com.tws.hunt.stages.result;

import java.util.Map;
import java.util.Map.Entry;

public class CountByCategory extends HuntGameResult {

    private final Map<String, Integer> count;

    public CountByCategory(Map<String, Integer> count) {
        this.count = count;
    }

    @Override
    public String toJson() {
        StringBuilder json = new StringBuilder();
        json.append("{");
        for (Entry<String, Integer> e : count.entrySet()) {
            json.append("\"").append(e.getKey()).append("\" : ").append(e.getValue()).append(",");
        }

        json.deleteCharAt(json.length() - 1); // remove last comma

        json.append("}");
        return json.toString();
    }
}
