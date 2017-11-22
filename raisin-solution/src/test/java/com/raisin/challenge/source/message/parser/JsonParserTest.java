package com.raisin.challenge.source.message.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raisin.challenge.source.message.json.JsonDoneMessage;
import com.raisin.challenge.source.message.json.JsonIdMessage;

public class JsonParserTest {

    private final MessageParser jsonParser;

    public JsonParserTest() {
        jsonParser = new MessageParser(new ObjectMapper());
    }

    @Test
    public void validIdMessageIsParsedSucessfully() {
        JsonIdMessage idMsg = jsonParser.parse("{ \"status\": \"ok\", \"id\": \"12345\" }", JsonIdMessage.class);
        assertEquals("ok", idMsg.getStatus());
        assertEquals("12345", idMsg.getId());
    }

    @Test
    public void invalidIdMessageReturnsNullOnParsing() {
        JsonIdMessage idMsg = jsonParser.parse("{ \"status\": \"ok\", \"idXXX\": \"12345\" }", JsonIdMessage.class);
        assertEquals(null, idMsg);
    }

    @Test
    public void validDoneMessageIsParsedSucessfully() {
        JsonDoneMessage doneMsg = jsonParser.parse("{\"status\": \"done\"}", JsonDoneMessage.class);
        assertEquals("done", doneMsg.getStatus());
    }

    @Test
    public void invalidDoneMessageReturnsNullOnParsing() {
        JsonDoneMessage doneMsg = jsonParser.parse("{\"statusXXX\": \"done\"}", JsonDoneMessage.class);
        assertEquals(null, doneMsg);
    }
}
