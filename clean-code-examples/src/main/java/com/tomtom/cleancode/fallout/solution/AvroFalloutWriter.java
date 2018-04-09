package com.tomtom.cleancode.fallout.solution;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;

import com.tomtom.places.unicorn.domain.avro.archive.ArchiveFallout;

public class AvroFalloutWriter extends FalloutWriter {

    private DataFileWriter<ArchiveFallout> falloutDatafileWriter;

    public AvroFalloutWriter(String falloutPath, String falloutFileName) {
        super(falloutPath, falloutFileName);
    }

    @Override
    public void initWriter() throws IOException {
        DatumWriter<ArchiveFallout> datumWriter = new SpecificDatumWriter<ArchiveFallout>(ArchiveFallout.class);
        falloutDatafileWriter = new DataFileWriter<ArchiveFallout>(datumWriter);
        OutputStream falloutOutputStream = new FileOutputStream(falloutPath + File.separator + falloutFileName, true);
        falloutDatafileWriter.create(ArchiveFallout.SCHEMA$, falloutOutputStream);

    }

    @Override
    public void writeFallout(ArchiveFallout fallout) throws IOException {
        falloutDatafileWriter.append(fallout);
    }

    @Override
    public void flush() throws IOException {
        falloutDatafileWriter.flush();
    }

    @Override
    public void close() throws IOException {
        falloutDatafileWriter.close();
    }
}
