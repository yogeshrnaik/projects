package com.tomtom.cleancode.fallout.solution;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.tomtom.places.unicorn.domain.avro.archive.ArchiveFallout;

public class FalloutWriterService implements Closeable {

    private static final Logger LOGGER = Logger.getLogger(FalloutWriterService.class);

    private final String falloutPath;
    private final FalloutWriterGroup suppressedViolationsWritersGroup;
    private final FalloutWriterGroup falloutWritersGroup;

    public FalloutWriterService(String falloutPath, FalloutWriterGroup suppressedViolationsWritersGroup,
        FalloutWriterGroup falloutWritersGroup) {
        this.falloutPath = falloutPath;
        this.suppressedViolationsWritersGroup = suppressedViolationsWritersGroup;
        this.falloutWritersGroup = falloutWritersGroup;
    }

    public static FalloutWriterService getFallOutWriterService(String falloutPath) {
        return new FalloutWriterService(falloutPath,
            new FalloutWriterGroup(new CsvFalloutWriter(falloutPath, "suppressedviolations.txt")),
            new FalloutWriterGroup(
                new CsvFalloutWriter(falloutPath, "fallout.txt"),
                new AvroFalloutWriter(falloutPath, "fallout.avro")));
    }

    public void initializeWriters() throws IOException {
        LOGGER.debug("Initializing fallouts in path " + falloutPath);
        suppressedViolationsWritersGroup.initializeWriters();
        falloutWritersGroup.initializeWriters();
    }

    public void writeAndClearFallouts() throws IOException {
        suppressedViolationsWritersGroup.writeFallouts();
        falloutWritersGroup.writeFallouts();
    }

    public void addFallouts(List<ArchiveFallout> archiveFalloutList) {
        falloutWritersGroup.addFallouts(archiveFalloutList);
    }

    public void addSuppressedViolations(List<ArchiveFallout> archiveFalloutList) {
        suppressedViolationsWritersGroup.addFallouts(archiveFalloutList);
    }

    @Override
    public void close() throws IOException {
        IOUtils.closeQuietly(falloutWritersGroup);
        IOUtils.closeQuietly(suppressedViolationsWritersGroup);
    }
}
