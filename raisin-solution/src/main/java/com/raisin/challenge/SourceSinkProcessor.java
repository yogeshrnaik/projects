package com.raisin.challenge;

import static com.raisin.challenge.util.PropertyFileReader.getPropertyIntgerValue;
import static com.raisin.challenge.util.PropertyFileReader.getPropertyValue;
import static com.raisin.challenge.util.Util.formatTimeInHoursMinutesAndSeconds;
import static java.lang.System.currentTimeMillis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.raisin.challenge.source.SourceProcessor;
import com.raisin.challenge.source.SourceReader;
import com.raisin.challenge.source.message.SourceMessageQueue;
import com.raisin.challenge.source.message.parser.JsonMessageParser;
import com.raisin.challenge.source.message.parser.MessageParser;
import com.raisin.challenge.source.message.parser.XmlMessageParser;
import com.raisin.challenge.source.sink.SinkData;
import com.raisin.challenge.source.sink.SinkProcessor;
import com.raisin.challenge.util.ThreadUtil;

/**
 * Main class that ties everything together. This class initialized all the shared resources. <br/>
 * It triggers 3 threads (1 for source A, 1 for source B and 1 for sink) <br/>
 * It then waits for all threads to be finished.
 */
public class SourceSinkProcessor {

    private static final Logger LOGGER = Logger.getLogger(SourceSinkProcessor.class);

    private static final String SOURCE_A = "A";
    private static final String SOURCE_B = "B";

    private final SourceMessageQueue msgQueue;
    private final SinkData sinkData;
    private final Object lock;

    public SourceSinkProcessor() {
        msgQueue = new SourceMessageQueue(getPropertyIntgerValue("source.message.queue.size"));
        sinkData = new SinkData(SOURCE_A, SOURCE_B);
        lock = new Object();
    }

    public static void main(String[] args) {
        long startTime = currentTimeMillis();
        LOGGER.info("Application started.");
        new SourceSinkProcessor().process();
        LOGGER.info(String.format("Application finished in [%s].", formatTimeInHoursMinutesAndSeconds(currentTimeMillis() - startTime)));
    }

    public void process() {
        // start 3 threads - 1 for source A and 1 for source B and 1 for sink
        ExecutorService execService = Executors.newFixedThreadPool(3);
        startSourceProcessor(SOURCE_A, new JsonMessageParser(), execService);
        startSourceProcessor(SOURCE_B, new XmlMessageParser(), execService);
        startSinkProcessor(execService);
        ThreadUtil.awaitTermination(execService);
    }

    private void startSourceProcessor(String source, MessageParser msgParser, ExecutorService execService) {
        String sourceUrl = getSourceUrl(source);
        SourceReader reader = new SourceReader(source, sourceUrl, msgParser);
        SourceProcessor processor = new SourceProcessor(source, sourceUrl, msgQueue, reader, sinkData, lock);
        execService.submit(processor);
    }

    private static String getSourceUrl(String source) {
        return getPropertyValue(String.format("source.%s.url", source.toLowerCase()));
    }

    private void startSinkProcessor(ExecutorService execService) {
        String sinkUrl = getPropertyValue("sink.url");
        SinkProcessor sinkProcessor = new SinkProcessor(sinkUrl, msgQueue, sinkData, lock);
        execService.submit(sinkProcessor);
    }
}
