package com.raisin.challenge.source;

import org.apache.log4j.Logger;

import com.raisin.challenge.util.PropertyFileReader;
import com.raisin.challenge.util.RaisinUtil;

public class SourceReader implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(SourceReader.class);

    private final String source;
    private final String sourceUrl;
    private boolean isDone;

    public SourceReader(String source) {
        this.source = source;
        this.sourceUrl = PropertyFileReader.getPropertyValue(String.format("source.%s.url", source.toLowerCase()));
        isDone = false;
    }

    public void run() {
        Thread.currentThread().setName("SourceReader_" + source);
        while (!isDone) {
            try {
                read();
            } catch (Throwable t) {
                LOGGER.warn(String.format("Error occurred while reading from Source URL: [%s]", sourceUrl), t);
                RaisinUtil.sleep(5000);
            }
        }
    }

    private void read() {

    }
}
