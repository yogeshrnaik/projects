package com.raisin.challenge.source;

import static com.raisin.challenge.util.Util.is406Error;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.raisin.challenge.source.message.MessageDto;
import com.raisin.challenge.source.message.parser.MessageParser;

public class SourceReader {

    private static final Logger LOGGER = Logger.getLogger(SourceReader.class);

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

            MessageDto msg = msgParser.parse(source, messageBody);
            return new SourceResponse(statusCode, messageBody, msg);
        } catch (Throwable t) {
            if (is406Error(t)) {
                LOGGER.warn(String.format("Error while reading from source: [%s], URL: [%s]", source, sourceUrl), t);
                return new SourceResponse(406, "");
            }
            throw new RuntimeException(String.format("Error while reading from source: [%s], URL: [%s]", source, sourceUrl), t);
        }
    }
}
