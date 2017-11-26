package com.raisin.challenge.source;

import static com.raisin.challenge.util.ThreadUtil.notifyOthers;
import static com.raisin.challenge.util.ThreadUtil.waitTillNotified;
import static com.raisin.challenge.util.Util.isConnectionClosed;

import org.apache.log4j.Logger;

import com.raisin.challenge.exception.NotAcceptableException;
import com.raisin.challenge.source.message.SourceMessage;
import com.raisin.challenge.source.message.SourceMessageQueue;
import com.raisin.challenge.source.sink.SinkData;

/**
 * Reads from source and puts the message in source queue. <br/>
 * In case of 406 error notifies others and waits till it is notified by some other thread. <br/>
 * On receiving DONE message, puts the message into source message queue and waits till it is notified by some other thread.
 */
public class SourceProcessor implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(SourceProcessor.class);

    private final String source;
    private final String sourceUrl;
    private final SourceMessageQueue msgQueue;
    private final SourceReader sourceReader;
    private final SinkData sinkData;
    private final Object lock;

    public SourceProcessor(String source, String sourceUrl, SourceMessageQueue msgQueue, SourceReader sourceReader, SinkData sinkData,
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
        process();
        LOGGER.info(String.format("Source [%s] processing finished.", source));
    }

    private void process() {
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
        addToQueue(response.getSourceMessage());
        if (isDone) {
            waitTillNotified(lock);
        }
    }

    private void addToQueue(SourceMessage msg) {
        msgQueue.add(msg);
    }

}
