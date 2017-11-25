package com.raisin.challenge.source;

import static com.raisin.challenge.util.ThreadUtil.notifyOthers;
import static com.raisin.challenge.util.ThreadUtil.waitTillNotified;
import static com.raisin.challenge.util.Util.isConnectionClosed;

import org.apache.log4j.Logger;

import com.raisin.challenge.exception.NotAcceptableException;
import com.raisin.challenge.source.message.MessageDto;
import com.raisin.challenge.source.message.MessageQueue;
import com.raisin.challenge.source.sink.SinkData;
import com.raisin.challenge.util.ThreadUtil;

public class SourceProcessor implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(SourceProcessor.class);

    private final String source;
    private final String sourceUrl;
    private final MessageQueue msgQueue;
    private final SourceReader sourceReader;
    private final SinkData sinkData;
    private final Object lock;

    public SourceProcessor(String source, String sourceUrl, MessageQueue msgQueue, SourceReader sourceReader, SinkData sinkData,
        Object lock) {
        this.source = source;
        this.sourceUrl = sourceUrl;
        this.msgQueue = msgQueue;
        this.sourceReader = sourceReader;
        this.sinkData = sinkData;
        this.lock = lock;
    }

    public void run() {
        Thread.currentThread().setName("SourceProcessor_" + source);
        processUntilNotDone();
        LOGGER.info(String.format("Source [%s] processing finished.", source));
    }

    private void processUntilNotDone() {
        while (sinkData.notAllDataProcessed()) {
            try {
                processMessage(sourceReader.read());
            } catch (NotAcceptableException e) {
                notifyOthers(lock);
                waitTillNotified(lock);
            } catch (Throwable t) {
                LOGGER.warn(String.format("Error occurred while processing from Source URL: [%s]", sourceUrl), t);
                if (isConnectionClosed(t))
                    return;
                ThreadUtil.sleep(1000);
            }
        }
    }

    private void processMessage(SourceResponse response) {
        if (response.isValid()) {
            processValidMessage(response);
        } else {
            LOGGER.warn(String.format("Ignoring defective message: [%s]", response.getRawResponse()));
        }
    }

    private void processValidMessage(SourceResponse response) {
        LOGGER.info(String.format("Message received: [%s]", response.getRawResponse()));
        boolean isDone = response.isDone();
        addToQueue(response.getMessageDto());
        if (isDone) {
            waitTillNotified(lock);
        }
    }

    private void addToQueue(MessageDto msg) {
        msgQueue.add(msg);
    }

}
