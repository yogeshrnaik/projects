package com.tws.hunt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties
@Configuration
public class HuntGameConfig {

    @Value("${userId}")
    private String userId;

    @Value("${hunt.game.input.url}")
    private String inputUrl;

    @Value("${hunt.game.output.url}")
    private String outputUrl;

    public String getUserId() {
        return userId;
    }

    public String getInputUrl() {
        return inputUrl;
    }

    public String getOutputUrl() {
        return outputUrl;
    }
}
