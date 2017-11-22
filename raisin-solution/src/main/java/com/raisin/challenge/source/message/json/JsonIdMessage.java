package com.raisin.challenge.source.message.json;

public class JsonIdMessage {

    private String id;
    private String status;

    public JsonIdMessage() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "JsonIdMessage [id=" + id + ", status=" + status + "]";
    }
}
