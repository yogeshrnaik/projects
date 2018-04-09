package com.tomtom.cleancode.fallout.solution;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.tomtom.places.unicorn.domain.avro.archive.ArchiveFallout;

public class FallOutWriterService implements Closeable {

    private static final Logger LOGGER = Logger.getLogger(FallOutWriterService.class);

    private final String falloutPath;
    private final FalloutWriterGroup suppressedViolationsWriters;
    private final FalloutWriterGroup falloutWriters;

    public FallOutWriterService(String falloutPath, FalloutWriterGroup suppressedViolationsWriters, FalloutWriterGroup falloutWriters) {
        this.falloutPath = falloutPath;
        this.suppressedViolationsWriters = suppressedViolationsWriters;
        this.falloutWriters = falloutWriters;
    }

    public static FallOutWriterService getFallOutWriterService(String falloutPath) {
        return new FallOutWriterService(falloutPath,
            new FalloutWriterGroup(new CsvFalloutWriter(falloutPath, "suppressedviolations.txt")),
            new FalloutWriterGroup(
                new CsvFalloutWriter(falloutPath, "fallout.txt"),
                new AvroFalloutWriter(falloutPath, "fallout.avro")));
    }

    public void initializeWriters() throws IOException {
        LOGGER.debug("Initializing fallouts in path " + falloutPath);
        suppressedViolationsWriters.initializeWriters();
        falloutWriters.initializeWriters();
    }

    public void writeAndClearFallouts() throws IOException {
        suppressedViolationsWriters.writeFallouts();
        falloutWriters.writeFallouts();
    }

    public void addFallouts(List<ArchiveFallout> archiveFalloutList) {
        falloutWriters.addFallouts(archiveFalloutList);
    }

    public void addSuppressedViolations(List<ArchiveFallout> archiveFalloutList) {
        suppressedViolationsWriters.addFallouts(archiveFalloutList);
    }

    @Override
    public void close() throws IOException {
        falloutWriters.close();
        suppressedViolationsWriters.close();
    }
}
