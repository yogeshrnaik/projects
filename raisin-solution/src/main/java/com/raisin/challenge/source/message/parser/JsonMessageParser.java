package com.raisin.challenge.source.message.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raisin.challenge.source.message.MessageDto;
import com.raisin.challenge.source.message.json.JsonDoneMessage;
import com.raisin.challenge.source.message.json.JsonIdMessage;

public class JsonMessageParser extends MessageParser {

    public JsonMessageParser() {
        super(new ObjectMapper());
    }

    public MessageDto parse(String source, String message) {
        JsonDoneMessage doneMsg = parse(message, JsonDoneMessage.class);
        if (doneMsg != null)
            return new MessageDto(source, true);

        JsonIdMessage idMsg = parse(message, JsonIdMessage.class);
        if (idMsg != null)
            return new MessageDto(source, idMsg.getId());

        return null;
    }

}
