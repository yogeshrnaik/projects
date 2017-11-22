package com.raisin.challenge;

import org.apache.log4j.Logger;

import com.raisin.challenge.source.SourceReader;
import com.raisin.challenge.source.message.parser.JsonMessageParser;
import com.raisin.challenge.source.message.parser.MessageParser;
import com.raisin.challenge.source.message.parser.XmlMessageParser;
import com.raisin.challenge.util.RaisinUtil;

public class RaisinSolution {

    private static final Logger LOGGER = Logger.getLogger(RaisinSolution.class);

    public static void main(String[] args) {
        startSourceReader("A", new JsonMessageParser());
        startSourceReader("B", new XmlMessageParser());
        RaisinUtil.sleep(100000000000L);
    }

    private static void startSourceReader(String source, MessageParser parser) {
        SourceReader reader = new SourceReader(source, parser);
        new Thread(reader).start();
    }
}
