package com.raisin.challenge.source.message.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.raisin.challenge.source.message.SourceMessage;

public class JsonMessageParserTest {

    private final JsonMessageParser jsonParser;

    public JsonMessageParserTest() {
        jsonParser = new JsonMessageParser();
    }

    @Test
    public void validIdMessageIsParsedSucessfully() {
        SourceMessage msg = jsonParser.parse("jsonSource", "{ \"status\": \"ok\", \"id\": \"12345\" }");
        assertEquals("jsonSource", msg.getSource());
        assertEquals("12345", msg.getId());
        assertFalse(msg.isDone());
    }

    @Test
    public void invalidIdMessageReturnsNullOnParsing() {
        SourceMessage msg = jsonParser.parse("jsonSource", "{ \"status\": \"ok\", \"idXXX\": \"12345\" }");
        assertEquals(null, msg);
    }

    @Test
    public void validDoneMessageIsParsedSucessfully() {
        SourceMessage msg = jsonParser.parse("jsonSource", "{\"status\": \"done\"}");
        assertEquals("jsonSource", msg.getSource());
        assertEquals(null, msg.getId());
        assertTrue(msg.isDone());
    }

    @Test
    public void invalidDoneMessageReturnsNullOnParsing() {
        SourceMessage msg = jsonParser.parse("jsonSource", "{\"statusXXX\": \"done\"}");
        assertEquals(null, msg);
    }
}
