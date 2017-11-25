package com.raisin.challenge.sink;

public class SinkMessage {

    private final String id;
    private final String recortType;

    public SinkMessage(String id, String recortType) {
        this.id = id;
        this.recortType = recortType;
    }

    public String getId() {
        return id;
    }

    public String getRecortType() {
        return recortType;
    }

    @Override
    public String toString() {
        return "SinkMessage [id=" + id + ", recortType=" + recortType + "]";
    }
}
