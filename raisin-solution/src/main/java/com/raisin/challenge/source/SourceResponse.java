package com.raisin.challenge.source;

import org.springframework.http.HttpStatus;

import com.raisin.challenge.source.message.MessageDto;

public class SourceResponse {

    private final int statusCode;
    private final String rawResponse;
    private final MessageDto messageDto;

    public SourceResponse(int statusCode, String rawResponse) {
        this(statusCode, rawResponse, null);
    }

    public SourceResponse(int statusCode, String rawResponse, MessageDto messageDto) {
        this.statusCode = statusCode;
        this.rawResponse = rawResponse;
        this.messageDto = messageDto;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getRawResponse() {
        return rawResponse;
    }

    public MessageDto getMessageDto() {
        return messageDto;
    }

    public boolean isNotAcceptable() {
        return statusCode == HttpStatus.NOT_ACCEPTABLE.value();
    }

    public boolean isValid() {
        return messageDto != null;
    }

    public boolean isDone() {
        return isValid() && messageDto.isDone();
    }
}
