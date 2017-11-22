package com.raisin.challenge.source.message.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.raisin.challenge.source.message.xml.XmlDoneMessage;
import com.raisin.challenge.source.message.xml.XmlIdMessage;

public class XmlParserTest {

    private final MessageParser parser;

    public XmlParserTest() {
        parser = new MessageParser(new XmlMapper());
    }

    @Test
    public void validIdMessageIsParsedSucessfully() {
        XmlIdMessage idMsg =
            parser.parse("<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><id value=\"111\"/></msg>", XmlIdMessage.class);
        assertEquals("111", idMsg.getMessageId().getValue());
    }

    @Test
    public void invalidIdMessageReturnsNullOnParsing() {
        XmlIdMessage idMsg =
            parser.parse("<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><idXXX value=\"111\"/></msg>", XmlIdMessage.class);
        assertEquals(null, idMsg);
    }

    @Test
    public void validDoneMessageIsParsedSucessfully() {
        XmlDoneMessage doneMsg = parser.parse("<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><done/></msg>", XmlDoneMessage.class);
        assertEquals("", doneMsg.getDone());
    }

    @Test
    public void invalidDoneMessageReturnsNullOnParsing() {
        XmlDoneMessage doneMsg = parser.parse("<?xml version=\"1.0\" encoding=\"UTF-8\"?><msgXXX><done/></msg>", XmlDoneMessage.class);
        assertEquals(null, doneMsg);
    }
}
