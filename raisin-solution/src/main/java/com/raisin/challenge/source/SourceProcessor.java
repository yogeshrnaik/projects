package com.raisin.challenge.source;

import org.apache.log4j.Logger;

import com.raisin.challenge.source.message.MessageQueue;
import com.raisin.challenge.source.message.parser.MessageParser;
import com.raisin.challenge.util.ThreadUtil;

public class SourceProcessor implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(SourceProcessor.class);

    private final String source;
    private final String sourceUrl;
    private boolean isDone;
    private final MessageQueue msgQueue;
    private final SourceReader sourceReader;

    public SourceProcessor(String source, String sourceUrl, MessageQueue msgQueue, MessageParser msgParser) {
        this.source = source;
        this.sourceUrl = sourceUrl;
        isDone = false;
        this.msgQueue = msgQueue;
        sourceReader = new SourceReader(source, sourceUrl, msgParser);
    }

    public void run() {
        Thread.currentThread().setName("SourceProcessor_" + source);
        processUntilNotDone();
        LOGGER.info(String.format("Source [%s] processing finished.", source));
    }

    private void processUntilNotDone() {
        while (!isDone) {
            try {
                processMessage();
            } catch (Throwable t) {
                LOGGER.warn(String.format("Error occurred while processing from Source URL: [%s]", sourceUrl), t);
                ThreadUtil.sleep(5000);
            }
        }
    }

    private void processMessage() {
        SourceResponse response = sourceReader.read();
        if (response.isNotAcceptable()) {
            throw new RuntimeException(String.format("Response from source [%s] is not acceptable.", source));
        }

        if (response.isValid()) {
            LOGGER.info(String.format("Message received: [%s]", response.getRawResponse()));
            isDone = response.isDone();
            msgQueue.add(response.getMessageDto());
        } else {
            LOGGER.warn(String.format("Ignoring defective message: [%s]", response.getRawResponse()));
        }
    }
}
