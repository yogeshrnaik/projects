package com.raisin.challenge.source.message;

public class SourceMessage {

    private final String source;
    private final String id;
    private final boolean done;

    public SourceMessage(String source, String id) {
        this.source = source;
        this.id = id;
        this.done = false;
    }

    public SourceMessage(String source, boolean done) {
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
        return done ? String.format("SourceMessage [source=%s, done=%s]", source, done)
            : String.format("SourceMessage [source=%s, id=%s]", source, id);
    }
}
