package com.raisin.challenge.source.message.parser;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageParser {

    private final ObjectMapper objectMapper;

    public MessageParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T parse(String data, Class<T> clazz) {
        try {
            return objectMapper.readValue(data, clazz);
        } catch (IOException e) {
            return null;
        }
    }
}
