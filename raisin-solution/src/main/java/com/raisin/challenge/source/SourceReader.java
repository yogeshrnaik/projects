package com.raisin.challenge.source;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.raisin.challenge.source.message.MessageDto;
import com.raisin.challenge.source.message.parser.MessageParser;

public class SourceReader {

    private final String source;
    private final String sourceUrl;
    private MessageParser msgParser;
    private final RestTemplate restTemplate;

    public SourceReader(String source, String sourceUrl, MessageParser msgParser) {
        this.source = source;
        this.sourceUrl = sourceUrl;
        this.msgParser = msgParser;
        restTemplate = new RestTemplate();
    }

    public SourceResponse read() {
        ResponseEntity<String> response = restTemplate.getForEntity(sourceUrl, String.class);

        String messageBody = response.getBody();
        int statusCode = response.getStatusCode().value();

        if (statusCode == HttpStatus.NOT_ACCEPTABLE.value()) {
            return new SourceResponse(statusCode, messageBody);
        }

        MessageDto msg = msgParser.parse(source, messageBody);
        return new SourceResponse(statusCode, messageBody, msg);
    }
}
