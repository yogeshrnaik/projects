package com.tomtom.cleancode.fallout.solution;

import java.io.Closeable;
import java.io.IOException;

import com.tomtom.places.unicorn.domain.avro.archive.ArchiveFallout;

public abstract class FalloutWriter implements Closeable {

    protected final String falloutPath;
    protected final String falloutFileName;

    public FalloutWriter(String falloutPath, String falloutFileName) {
        this.falloutPath = falloutPath;
        this.falloutFileName = falloutFileName;
    }

    public abstract void initWriter() throws IOException;

    public abstract void writeFallout(ArchiveFallout fallout) throws IOException;

    public abstract void flush() throws IOException;

    public String getFalloutPath() {
        return falloutPath;
    }

    public String getFalloutFileName() {
        return falloutFileName;
    }
}
