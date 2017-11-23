package com.raisin.challenge.source.sink;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import com.raisin.challenge.RaisinSolution;

public class SinkWriter {

    private static final Logger LOGGER = Logger.getLogger(RaisinSolution.class);

    private final String sinkUrl;
    private final RestTemplate restTemplate;

    public SinkWriter(String sinkUrl) {
        super();
        this.sinkUrl = sinkUrl;
        restTemplate = new RestTemplate();
    }

    public void write(String id, String recordType) {
        String body = String.format("{\"kind\": \"%s\", \"id\": \"%s\"}", recordType, id);
        post(body);
    }

    private void post(String body) {
        HttpEntity<String> request = new HttpEntity<>(body);
        String response = restTemplate.postForObject(sinkUrl, request, String.class);
        LOGGER.info(String.format("Sucessfully posted [%s] to url [%s]. Response: [%s]", body, sinkUrl, response));
    }

}
