package com.raisin.challenge.source.message.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.raisin.challenge.source.message.SourceMessage;

public class XmlMessageParserTest {

    private final XmlMessageParser xmlParser;

    public XmlMessageParserTest() {
        xmlParser = new XmlMessageParser();
    }

    @Test
    public void validIdMessageIsParsedSucessfully() {
        SourceMessage msg = xmlParser.parse("xmlSource", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><id value=\"54321\"/></msg>");
        assertEquals("xmlSource", msg.getSource());
        assertEquals("54321", msg.getId());
        assertFalse(msg.isDone());
    }

    @Test
    public void invalidIdMessageReturnsNullOnParsing() {
        SourceMessage msg = xmlParser.parse("xmlSource", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><idXXX value=\"111\"/></msg>");
        assertEquals(null, msg);
    }

    @Test
    public void validDoneMessageIsParsedSucessfully() {
        SourceMessage msg = xmlParser.parse("xmlSource", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><done/></msg>");
        assertEquals("xmlSource", msg.getSource());
        assertEquals(null, msg.getId());
        assertTrue(msg.isDone());
    }

    @Test
    public void invalidDoneMessageReturnsNullOnParsing() {
        SourceMessage msg = xmlParser.parse("xmlSource", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><msgXXX><done/></msg>");
        assertEquals(null, msg);
    }
}
