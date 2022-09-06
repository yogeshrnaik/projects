package com.atlassian.ratelimiter.educative.sysdesign;

import java.util.concurrent.TimeUnit;

class TokenBucket implements RateLimiter {

    private int MAX_TOKENS;
    private long lastRequestTime;
    long availableTokens = 0;
    int refillRatePerTimeUnit;
    TimeUnit timeUnit;

    public TokenBucket(int maxTokens, int refillRatePerTimeUnit, TimeUnit timeUnit) {
        MAX_TOKENS = maxTokens;
        availableTokens = maxTokens;
        this.refillRatePerTimeUnit = refillRatePerTimeUnit;
        lastRequestTime = System.currentTimeMillis();
        this.timeUnit = timeUnit;
    }

    public synchronized boolean isAllowed() {
        long numOfSecondsSinceLastRequest = (System.currentTimeMillis() - lastRequestTime) / getRefreshRatePerMillisecond();
        long newTokens = refillRatePerTimeUnit * numOfSecondsSinceLastRequest;
        this.availableTokens = Math.min(newTokens + availableTokens, MAX_TOKENS);
        if (this.availableTokens <= 0) {
            System.out.println("Not Granting " + Thread.currentThread().getName() + " token at " + System.currentTimeMillis());
            return false;
        } else {
            this.availableTokens--;
        }
        lastRequestTime = System.currentTimeMillis();
        System.out.println("Granting " + Thread.currentThread().getName() + " token at " + System.currentTimeMillis() + ", newTokens: " + newTokens);
        return true;
    }

    private int getRefreshRatePerMillisecond() {
        if (timeUnit == TimeUnit.SECONDS)
            return 1000;

        if (timeUnit == TimeUnit.MILLISECONDS)
            return refillRatePerTimeUnit;

        return refillRatePerTimeUnit;
    }
}

