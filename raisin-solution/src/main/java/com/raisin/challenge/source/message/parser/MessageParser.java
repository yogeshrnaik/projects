package com.raisin.challenge.source.message.parser;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raisin.challenge.source.message.SourceMessage;

public abstract class MessageParser {

    private final ObjectMapper objectMapper;

    public MessageParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    protected <T> T parse(String data, Class<T> clazz) {
        try {
            return objectMapper.readValue(data, clazz);
        } catch (IOException e) {
            return null;
        }
    }

    public abstract SourceMessage parse(String source, String message);
}
