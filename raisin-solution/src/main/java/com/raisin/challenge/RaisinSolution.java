package com.raisin.challenge;

import static com.raisin.challenge.util.PropertyFileReader.getPropertyIntgerValue;
import static com.raisin.challenge.util.PropertyFileReader.getPropertyValue;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    static {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        System.setProperty("current.date.time", dateFormat.format(new Date()));
    }

    private static final String SOURCE_B = "B";
    private static final String SOURCE_A = "A";
    private static final Logger LOGGER = Logger.getLogger(RaisinSolution.class);

    public static void main(String[] args) {
        LOGGER.info("Application started.");
        ExecutorService execService = Executors.newFixedThreadPool(3);
        MessageQueue msgQueue = new MessageQueue(getPropertyIntgerValue("queue.size"));
        SinkData sinkData = new SinkData(SOURCE_A, SOURCE_B);

        String sourceUrlA = getPropertyValue(String.format("source.%s.url", SOURCE_A.toLowerCase()));
        SourceReader readerA = new SourceReader(SOURCE_A, sourceUrlA, new JsonMessageParser());
        startSourceProcessor(SOURCE_A, readerA, msgQueue, sinkData, execService);

        String sourceUrlB = getPropertyValue(String.format("source.%s.url", SOURCE_B.toLowerCase()));
        SourceReader readerB = new SourceReader(SOURCE_B, sourceUrlB, new XmlMessageParser());
        startSourceProcessor(SOURCE_B, readerB, msgQueue, sinkData, execService);

        startSinkProcessor(1, msgQueue, sinkData, execService);

        ThreadUtil.awaitTermination(execService);
        LOGGER.info("Application finished.");
    }

    private static void startSourceProcessor(String source, SourceReader reader, MessageQueue msgQueue, SinkData sinkData,
        ExecutorService execService) {
        String sourceUrl = getPropertyValue(String.format("source.%s.url", source.toLowerCase()));
        SourceProcessor processor = new SourceProcessor(source, sourceUrl, msgQueue, reader, sinkData);
        execService.submit(processor);
    }

    private static void startSinkProcessor(int sinkId, MessageQueue msgQueue, SinkData sinkData, ExecutorService execService) {
        String sinkUrl = getPropertyValue("sink.url");
        SinkProcessor sinkProcessor = new SinkProcessor(sinkId, sinkUrl, msgQueue, sinkData);
        execService.submit(sinkProcessor);
    }
}
