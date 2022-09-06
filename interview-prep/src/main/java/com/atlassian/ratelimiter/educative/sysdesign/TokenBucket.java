package com.atlassian.ratelimiter.educative.sysdesign;

public class TokenBucket implements RateLimiter {

    private int maxTokens;
    private int availableTokens;
    private int refreshRateInMillis;
    private long lastRequestedTime;

    public TokenBucket(int tokens, int refreshRateInMillis) {
        this.maxTokens = tokens;
        this.refreshRateInMillis = refreshRateInMillis;
        this.availableTokens = tokens;
        lastRequestedTime = System.currentTimeMillis();
//        Thread dt = new Thread(() -> demonThreadToRefreshTokens());
//        dt.setDaemon(true);
//        dt.start();
    }

    @Override
    public synchronized boolean isAllowed() {
        return withOutDemonThread();
    }

    private synchronized boolean withDemonThread() {
        if (availableTokens > 0) {
            availableTokens--;
            return true;
        }
        return false;
    }

    private boolean withOutDemonThread() {
        long currentTime = System.currentTimeMillis();
        long timeDiffSinceLastReq = (currentTime - lastRequestedTime);
        if (timeDiffSinceLastReq >= refreshRateInMillis) {
            availableTokens = maxTokens;
            lastRequestedTime = currentTime;
        }
        if (availableTokens > 0) {
            availableTokens--;
            return true;
        }
        return false;
    }

    private void demonThreadToRefreshTokens() {
        while (true) {
            synchronized (this) {
                if (availableTokens < maxTokens) {
                    availableTokens = maxTokens;
                }
            }
            try {
                Thread.sleep(refreshRateInMillis);
            } catch (InterruptedException e) {
                // swallow
            }
        }
    }
}
