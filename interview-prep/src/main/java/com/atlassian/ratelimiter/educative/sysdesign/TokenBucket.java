package com.atlassian.ratelimiter.educative.sysdesign;

public class TokenBucket implements RateLimiter {

    private int maxTokens;
    private int availableTokens;
    private long lastRequestedTime;

    public TokenBucket(int tokens) {
        this.maxTokens = tokens;
        this.availableTokens = tokens;
        lastRequestedTime = System.currentTimeMillis();
    }

    @Override
    public synchronized boolean isAllowed() {
        long currentTime = System.currentTimeMillis();
        long timeDiffInSeconds = (currentTime - lastRequestedTime)/1000;
        if (timeDiffInSeconds >= 1) {
            availableTokens = maxTokens;
            lastRequestedTime = currentTime;
        }
        if (availableTokens > 0) {
            availableTokens--;
            return true;
        }

        return false;
    }
}
