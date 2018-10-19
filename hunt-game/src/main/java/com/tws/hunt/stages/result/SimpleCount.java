package com.tws.hunt.stages.result;

public class SimpleCount extends HuntGameResult {

    private final long count;

    public SimpleCount(long count) {
        this.count = count;
    }

    @Override
    public String toJson() {
        return "{\"count\": " + count + "}";
    }
}
