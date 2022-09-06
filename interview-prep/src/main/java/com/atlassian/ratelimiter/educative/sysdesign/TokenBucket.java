package com.atlassian.ratelimiter.educative.sysdesign;

import java.util.concurrent.TimeUnit;

class TokenBucket implements RateLimiter {

    private int MAX_TOKENS;
    private long lastRequestTime;
    long availableTokens = 0;
    int refillRate;
    int refillRateInMillis;

    public TokenBucket(int maxTokens, int refillRate, int refillRateInMillis) {
        MAX_TOKENS = maxTokens;
        availableTokens = maxTokens;
        this.refillRate = refillRate;
        lastRequestTime = System.currentTimeMillis();
        this.refillRateInMillis = refillRateInMillis;
    }

    public synchronized boolean isAllowed() {
        long numOfSecondsSinceLastRequest = (System.currentTimeMillis() - lastRequestTime) / refillRateInMillis;
        long newTokens = refillRate * numOfSecondsSinceLastRequest;
        this.availableTokens = Math.min(newTokens + availableTokens, MAX_TOKENS);
        if (this.availableTokens == 0) {
            System.out.println("Not Granting " + Thread.currentThread().getName() + " token at " + System.currentTimeMillis());
            return false;
        } else {
            this.availableTokens--;
        }
        lastRequestTime = System.currentTimeMillis();
        System.out.println("Granting " + Thread.currentThread().getName() + " token at " + System.currentTimeMillis() + ", newTokens: " + newTokens);
        return true;
    }
}

