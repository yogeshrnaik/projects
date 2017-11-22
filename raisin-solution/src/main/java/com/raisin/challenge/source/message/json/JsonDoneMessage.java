package com.raisin.challenge.source.message.json;

public class JsonDoneMessage {

    private String status;

    public JsonDoneMessage() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "JsonDoneMessage [status=" + status + "]";
    }
}
