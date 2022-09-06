package com.atlassian.ratelimiter.youtube;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TokenBucket implements RateLimiter {
    private int bucketCapacity;
    private int refreshRateInSeconds;
    private AtomicInteger currentCapacity;
    private AtomicLong lastUpdatedTime;

    public TokenBucket(int bucketCapacity, int refreshRate) {
        this.bucketCapacity = bucketCapacity;
        this.refreshRateInSeconds = refreshRate;
        currentCapacity = new AtomicInteger(bucketCapacity);
        lastUpdatedTime = new AtomicLong(System.currentTimeMillis());
    }

    @Override
    public boolean grantAccess() {
        refreshBucket();
        if (currentCapacity.get() > 0) {
            currentCapacity.decrementAndGet();
            return true;
        }
        return false;
    }

    void refreshBucket() {
        long currentTime = System.currentTimeMillis();
        int additionalToken = (int) ((currentTime - lastUpdatedTime.get()) / 1000 * refreshRateInSeconds);
        int currCapacity = Math.min(currentCapacity.get() + additionalToken, bucketCapacity);
        System.out.println("setting currCapacity: " + currCapacity + ", additionalToken: " + additionalToken);
        currentCapacity.getAndSet(currCapacity);
        lastUpdatedTime.getAndSet(currentTime);
    }
}