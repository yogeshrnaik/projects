package com.raisin.challenge.source.sink;

import static com.raisin.challenge.util.Util.is406Error;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.raisin.challenge.SourceSinkProcessor;
import com.raisin.challenge.exception.NotAcceptableException;

/**
 * Class that writes to Sink URL.
 */
public class SinkWriter {

    private static final Logger LOGGER = Logger.getLogger(SourceSinkProcessor.class);

    private final String sinkUrl;
    private final RestTemplate restTemplate;

    public SinkWriter(String sinkUrl) {
        super();
        this.sinkUrl = sinkUrl;
        // HttpComponentsClientHttpRequestFactory uses HTTP connection pooling internally
        restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

    /**
     * Posts data to Sink URL.
     * 
     * @param id
     * @param recordType
     * @throws in case of 406 error, {@link NotAcceptableException} else throws the original exception as is
     */
    public void write(String id, String recordType) {
        String body = String.format("{\"kind\": \"%s\", \"id\": \"%s\"}", recordType, id);
        post(body);
    }

    private void post(String body) {
        try {
            HttpEntity<String> request = new HttpEntity<>(body);
            String response = restTemplate.postForObject(sinkUrl, request, String.class);
            LOGGER.info(String.format("Sucessfully posted [%s] to url [%s]. Response: [%s]", body, sinkUrl, response));
        } catch (Throwable t) {
            if (is406Error(t)) {
                throw new NotAcceptableException(String.format("Error while posting [%s] to URL: [%s]. Error: [%s].", body, sinkUrl, t));
            }
            throw t;
        }
    }
}
