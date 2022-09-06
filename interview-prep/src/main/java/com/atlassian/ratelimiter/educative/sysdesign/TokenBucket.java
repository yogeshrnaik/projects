package com.atlassian.ratelimiter.educative.sysdesign;

class TokenBucket implements RateLimiter {

    private int max_tokens;
    private long lastRequestTime;
    private long availableTokens;
    private int refillRate;
    private int refillRateInMillis;

    public TokenBucket(int maxTokens, int refillRate, int refillRateInMillis) {
        max_tokens = maxTokens;
        availableTokens = maxTokens;
        this.refillRate = refillRate;
        lastRequestTime = System.currentTimeMillis();
        this.refillRateInMillis = refillRateInMillis;
    }

    public synchronized boolean isAllowed() {
        long newTokens = refillRate * ((System.currentTimeMillis() - lastRequestTime) / refillRateInMillis);
        this.availableTokens = Math.min(newTokens + availableTokens, max_tokens);
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

