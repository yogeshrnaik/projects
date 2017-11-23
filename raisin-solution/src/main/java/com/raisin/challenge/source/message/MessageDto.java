package com.raisin.challenge.source.message;

public class MessageDto {

    private final String source;
    private final String id;
    private final boolean done;

    public MessageDto(String source, String id) {
        this.source = source;
        this.id = id;
        this.done = false;
    }

    public MessageDto(String source, boolean done) {
        this.source = source;
        this.id = null;
        this.done = done;
    }

    public String getSource() {
        return source;
    }

    public String getId() {
        return id;
    }

    public boolean isDone() {
        return done;
    }

    @Override
    public String toString() {
        return "MessageDto [source=" + source + ", id=" + id + ", done=" + done + "]";
    }
}
