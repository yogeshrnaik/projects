package com.atlassian.ratelimiter.educative.sysdesign;

public class FixedWindow implements RateLimiter {

    private int maxTokens;
    private int availableTokens;
    private int timeWindowInMillis;
    private long lastRequestedTime;

    public FixedWindow(int tokens, int timeWindowInMillis) {
        this.maxTokens = tokens;
        this.timeWindowInMillis = timeWindowInMillis;
        this.availableTokens = tokens;
        lastRequestedTime = System.currentTimeMillis();
    }

    @Override
    public synchronized boolean isAllowed() {
        long currentTime = System.currentTimeMillis();
        long timeDiffSinceLastReq = (currentTime - lastRequestedTime);
        if (timeDiffSinceLastReq >= timeWindowInMillis) {
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
