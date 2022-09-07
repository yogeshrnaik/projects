package com.atlassian.ratelimiter.educative.sysdesign;

class TokenBucket implements RateLimiter {

    private int max_tokens;
    private long lastRefillTimeInMillis;
    private long availableTokens;
    private int refillRate;
    private int refillRateInMillis;

    public TokenBucket(int maxTokens, int refillRate, int refillRateInMillis) {
        max_tokens = maxTokens;
        availableTokens = maxTokens;
        this.refillRate = refillRate;
        lastRefillTimeInMillis = System.currentTimeMillis();
        this.refillRateInMillis = refillRateInMillis;
    }

    public synchronized boolean isAllowed() {
        long currentTimeMillis = System.currentTimeMillis();
        refillTokens();

        if (this.availableTokens == 0) {
            System.out.println("Not Granting " + Thread.currentThread().getName() + " token at " + currentTimeMillis);
            return false;
        } else {
            this.availableTokens--;
        }
        System.out.println("Granting " + Thread.currentThread().getName() + " token at " + currentTimeMillis);
        return true;
    }

    private void refillTokens() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - lastRefillTimeInMillis >= refillRateInMillis) {
            long newTokens = refillRate * ((currentTimeMillis - lastRefillTimeInMillis) / refillRateInMillis);
            this.availableTokens = Math.min(newTokens + availableTokens, max_tokens);
            lastRefillTimeInMillis = currentTimeMillis;
            System.out.println("New Tokens: " + newTokens + ", " + Thread.currentThread().getName() + " token at " + currentTimeMillis);
        }
    }
}
