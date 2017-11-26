package com.raisin.challenge.source;

import org.springframework.http.HttpStatus;

import com.raisin.challenge.source.message.SourceMessage;

public class SourceResponse {

    private final int statusCode;
    private final String rawResponse;
    private final SourceMessage sourceMessage;

    public SourceResponse(int statusCode, String rawResponse) {
        this(statusCode, rawResponse, null);
    }

    public SourceResponse(int statusCode, String rawResponse, SourceMessage sourceMessage) {
        this.statusCode = statusCode;
        this.rawResponse = rawResponse;
        this.sourceMessage = sourceMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getRawResponse() {
        return rawResponse;
    }

    public SourceMessage getSourceMessage() {
        return sourceMessage;
    }

    public boolean isNotAcceptable() {
        return statusCode == HttpStatus.NOT_ACCEPTABLE.value();
    }

    public boolean isValid() {
        return sourceMessage != null;
    }

    public boolean isDone() {
        return isValid() && sourceMessage.isDone();
    }
}
