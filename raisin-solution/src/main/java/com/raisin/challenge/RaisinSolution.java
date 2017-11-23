package com.raisin.challenge;

import static com.raisin.challenge.util.PropertyFileReader.getPropertyIntgerValue;
import static com.raisin.challenge.util.PropertyFileReader.getPropertyValue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.raisin.challenge.source.SourceProcessor;
import com.raisin.challenge.source.SourceReader;
import com.raisin.challenge.source.message.MessageQueue;
import com.raisin.challenge.source.message.parser.JsonMessageParser;
import com.raisin.challenge.source.message.parser.XmlMessageParser;
import com.raisin.challenge.source.sink.SinkData;
import com.raisin.challenge.source.sink.SinkProcessor;
import com.raisin.challenge.util.ThreadUtil;

public class RaisinSolution {

    private static final String SOURCE_B = "B";
    private static final String SOURCE_A = "A";
    private static final Logger LOGGER = Logger.getLogger(RaisinSolution.class);

    public static void main(String[] args) {
        LOGGER.info("Application started.");
        ExecutorService execService = Executors.newFixedThreadPool(3);
        MessageQueue msgQueue = new MessageQueue(getPropertyIntgerValue("queue.size"));

        String sourceUrlA = getPropertyValue(String.format("source.%s.url", SOURCE_A.toLowerCase()));
        SourceReader readerA = new SourceReader(SOURCE_A, sourceUrlA, new JsonMessageParser());
        startSourceProcessor(SOURCE_A, readerA, msgQueue, execService);

        String sourceUrlB = getPropertyValue(String.format("source.%s.url", SOURCE_B.toLowerCase()));
        SourceReader readerB = new SourceReader(SOURCE_B, sourceUrlB, new XmlMessageParser());
        startSourceProcessor(SOURCE_B, readerB, msgQueue, execService);

        SinkData sinkData = new SinkData(SOURCE_A, SOURCE_B);
        startSinkProcessor(1, msgQueue, sinkData, execService, readerA, readerB);

        ThreadUtil.awaitTermination(execService);
        LOGGER.info("Application finished.");
    }

    private static void startSourceProcessor(String source, SourceReader reader, MessageQueue msgQueue, ExecutorService execService) {
        String sourceUrl = getPropertyValue(String.format("source.%s.url", source.toLowerCase()));
        SourceProcessor processor = new SourceProcessor(source, sourceUrl, msgQueue, reader);
        execService.submit(processor);
    }

    private static void startSinkProcessor(int sinkId, MessageQueue msgQueue, SinkData sinkData, ExecutorService execService,
        SourceReader... readers) {
        String sinkUrl = getPropertyValue("sink.url");
        SinkProcessor sinkProcessor = new SinkProcessor(sinkId, sinkUrl, msgQueue, sinkData, readers);
        execService.submit(sinkProcessor);
    }
}
