package com.tomtom.cleancode.fallout.solution;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import com.tomtom.places.unicorn.domain.avro.archive.ArchiveFallout;

import au.com.bytecode.opencsv.CSVWriter;

public class CsvFalloutWriter extends FalloutWriter {

    private static final String[] HEADER = {"Sno", "Error_Type", "Transaction_Id", "Feature_Id", "Feature_Type", "Reason",
        "Violation_Message",
        "Violation_Variables", "Country", "State", "Rule_Id", "Version", "Rule_Behaviour", "Rule_Severity", "Rule_Link",
        "Failure_Messages", "Latitude", "Longitude", "External_Identifier", "Brand_Name", "Transaction_Type", "Failed_Transaction"};

    private static final Character TAB = '\t';

    private CSVWriter falloutCsvWriter;
    private long falloutSno = 0L;

    public CsvFalloutWriter(String falloutPath, String falloutFileName) {
        super(falloutPath, falloutFileName);
    }

    @Override
    public void initWriter() throws IOException {
        OutputStream falloutOutputStream = new FileOutputStream(falloutPath + File.separator + falloutFileName, true);
        Writer falloutFileWriter = new OutputStreamWriter(falloutOutputStream, "UTF-8");

        falloutCsvWriter = new CSVWriter(falloutFileWriter, TAB, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.NO_ESCAPE_CHARACTER);
        falloutCsvWriter.writeNext(HEADER);
        falloutCsvWriter.flush();
    }

    @Override
    public void writeFallout(ArchiveFallout fallout) throws IOException {
        falloutCsvWriter.writeAll(readableFileLines(falloutSno, fallout));
        falloutSno++;
    }

    private List<String[]> readableFileLines(long falloutSno, ArchiveFallout fallout) {
        return new FalloutFormatter().formatFallout(falloutSno, fallout);
    }

    @Override
    public void flush() throws IOException {
        falloutCsvWriter.flush();
    }

    @Override
    public void close() throws IOException {
        falloutCsvWriter.close();
    }
}
