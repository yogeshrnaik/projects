package com.raisin.challenge.source;

import static com.raisin.challenge.util.Util.is406Error;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.raisin.challenge.exception.NotAcceptableException;
import com.raisin.challenge.source.message.SourceMessage;
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
        restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

    public SourceResponse read() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(sourceUrl, String.class);

            String messageBody = response.getBody();
            int statusCode = response.getStatusCode().value();

            SourceMessage msg = msgParser.parse(source, messageBody);
            return new SourceResponse(statusCode, messageBody, msg);
        } catch (Throwable t) {
            if (is406Error(t)) {
                throw new NotAcceptableException(
                    String.format("Error while reading from source: [%s], URL: [%s]. Error: [%s].", source, sourceUrl, t));
            }
            throw t;
        }
    }
}
