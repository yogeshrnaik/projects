package com.raisin.challenge.source;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.raisin.challenge.source.message.MessageDto;
import com.raisin.challenge.source.message.parser.MessageParser;
import com.raisin.challenge.util.PropertyFileReader;
import com.raisin.challenge.util.RaisinUtil;

public class SourceReader implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(SourceReader.class);

    private final String source;
    private final String sourceUrl;
    private boolean isDone;
    private MessageParser msgParser;
    private final RestTemplate restTemplate;

    public SourceReader(String source, MessageParser msgParser) {
        this.source = source;
        this.sourceUrl = PropertyFileReader.getPropertyValue(String.format("source.%s.url", source.toLowerCase()));
        isDone = false;
        this.msgParser = msgParser;
        restTemplate = new RestTemplate();
    }

    public void run() {
        Thread.currentThread().setName("SourceReader_" + source);
        while (!isDone) {
            try {
                read();
            } catch (Throwable t) {
                LOGGER.warn(String.format("Error occurred while reading from Source URL: [%s]", sourceUrl), t);
                RaisinUtil.sleep(5000);
            }
        }
        LOGGER.info("Source reading finished.");
    }

    private void read() {
        ResponseEntity<String> response = restTemplate.getForEntity(sourceUrl, String.class);
        if (response.getStatusCode() != HttpStatus.NOT_ACCEPTABLE) {
            String messageBody = response.getBody();
            MessageDto msg = msgParser.parse(source, response.getBody());
            if (msg != null) {
                LOGGER.warn(String.format("Message received: [%s]", messageBody));
                isDone = msg.isDone();
            } else {
                LOGGER.warn(String.format("Ignoring defective message: [%s]", messageBody));
            }
        } else {
            RaisinUtil.sleep(5000);
        }
    }
}
