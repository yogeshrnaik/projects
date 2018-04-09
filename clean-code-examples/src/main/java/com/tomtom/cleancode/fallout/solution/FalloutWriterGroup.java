package com.tomtom.cleancode.fallout.solution;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.tomtom.places.unicorn.domain.avro.archive.ArchiveFallout;

public class FalloutWriterGroup implements Closeable {

    private final List<FalloutWriter> falloutWriters;

    private final Collection<ArchiveFallout> fallouts = new ArrayList<ArchiveFallout>();

    public FalloutWriterGroup(FalloutWriter... falloutWriters) {
        this.falloutWriters = Arrays.asList(falloutWriters);
    }

    public void initializeWriters() throws IOException {
        for (FalloutWriter writer : falloutWriters) {
            writer.initWriter();
        }
    }

    public void addFallouts(List<ArchiveFallout> archiveFalloutList) {
        synchronized (fallouts) {
            fallouts.addAll(archiveFalloutList);
        }
    }

    public void writeFallouts() throws IOException {
        synchronized (fallouts) {
            for (FalloutWriter writer : falloutWriters) {
                writeAndFlush(writer, fallouts);
            }
            fallouts.clear();
        }
    }

    private void writeAndFlush(FalloutWriter writer, Collection<ArchiveFallout> fallouts) throws IOException {
        for (ArchiveFallout fallout : fallouts) {
            writer.writeFallout(fallout);
        }
        writer.flush();
    }

    @Override
    public void close() throws IOException {
        closeWriters(falloutWriters);
    }

    private void closeWriters(List<FalloutWriter> writers) {
        for (FalloutWriter writer : writers) {
            IOUtils.closeQuietly(writer);
        }
    }
}
