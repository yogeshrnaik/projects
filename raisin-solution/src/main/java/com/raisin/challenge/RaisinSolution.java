package com.raisin.challenge;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.raisin.challenge.source.SourceProcessor;
import com.raisin.challenge.source.message.parser.JsonMessageParser;
import com.raisin.challenge.source.message.parser.MessageParser;
import com.raisin.challenge.source.message.parser.XmlMessageParser;
import com.raisin.challenge.util.PropertyFileReader;
import com.raisin.challenge.util.ThreadUtil;

public class RaisinSolution {

    private static final Logger LOGGER = Logger.getLogger(RaisinSolution.class);

    public static void main(String[] args) {
        LOGGER.info("Application started.");
        ExecutorService execService = Executors.newFixedThreadPool(3);
        startSourceProcessor("A", new JsonMessageParser(), execService);
        startSourceProcessor("B", new XmlMessageParser(), execService);
        ThreadUtil.awaitTermination(execService);
        LOGGER.info("Application finished.");
    }

    private static void startSourceProcessor(String source, MessageParser parser, ExecutorService execService) {
        String sourceUrl = PropertyFileReader.getPropertyValue(String.format("source.%s.url", source.toLowerCase()));
        SourceProcessor reader = new SourceProcessor(source, sourceUrl, parser);
        execService.submit(reader);
    }
}
