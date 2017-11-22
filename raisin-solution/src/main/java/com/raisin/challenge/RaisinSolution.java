package com.raisin.challenge;

import static com.raisin.challenge.util.PropertyFileReader.getPropertyIntgerValue;
import static com.raisin.challenge.util.PropertyFileReader.getPropertyValue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.raisin.challenge.source.SourceProcessor;
import com.raisin.challenge.source.message.MessageQueue;
import com.raisin.challenge.source.message.parser.JsonMessageParser;
import com.raisin.challenge.source.message.parser.MessageParser;
import com.raisin.challenge.source.message.parser.XmlMessageParser;
import com.raisin.challenge.source.sink.SinkProcessor;
import com.raisin.challenge.util.ThreadUtil;

public class RaisinSolution {

    private static final Logger LOGGER = Logger.getLogger(RaisinSolution.class);

    public static void main(String[] args) {
        LOGGER.info("Application started.");
        ExecutorService execService = Executors.newFixedThreadPool(3);
        MessageQueue msgQueue = new MessageQueue(getPropertyIntgerValue("queue.size"));
        startSourceProcessor("A", new JsonMessageParser(), msgQueue, execService);
        startSourceProcessor("B", new XmlMessageParser(), msgQueue, execService);
        startSinkProcessor(msgQueue, execService, "A", "B");
        ThreadUtil.awaitTermination(execService);
        LOGGER.info("Application finished.");
    }

    private static void startSourceProcessor(String source, MessageParser parser, MessageQueue msgQueue, ExecutorService execService) {
        String sourceUrl = getPropertyValue(String.format("source.%s.url", source.toLowerCase()));
        SourceProcessor reader = new SourceProcessor(source, sourceUrl, msgQueue, parser);
        execService.submit(reader);
    }

    private static void startSinkProcessor(MessageQueue msgQueue, ExecutorService execService, String... sources) {
        String sinkUrl = getPropertyValue("sink.url");
        SinkProcessor sinkProcessor = new SinkProcessor(sinkUrl, msgQueue, sources);
        execService.submit(sinkProcessor);
    }
}
