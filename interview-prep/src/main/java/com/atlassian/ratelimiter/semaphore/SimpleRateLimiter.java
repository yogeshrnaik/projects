package com.atlassian.ratelimiter.semaphore;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

// https://www.javacodegeeks.com/2017/07/basic-api-rate-limiting.html

public class SimpleRateLimiter {
    private Semaphore semaphore;
    private int maxPermits;
    private TimeUnit timePeriod;
    private ScheduledExecutorService scheduler;

    public static SimpleRateLimiter create(int permits, TimeUnit timePeriod) {
        SimpleRateLimiter limiter = new SimpleRateLimiter(permits, timePeriod);
        limiter.schedulePermitReplenishment();
        return limiter;
    }

    private SimpleRateLimiter(int permits, TimeUnit timePeriod) {
        this.semaphore = new Semaphore(permits);
        this.maxPermits = permits;
        this.timePeriod = timePeriod;
    }

    public boolean tryAcquire() {
        return semaphore.tryAcquire();
    }

    public void stop() {
        scheduler.shutdownNow();
    }

    public void schedulePermitReplenishment() {
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            int permits = maxPermits - semaphore.availablePermits();
            System.out.println("releasing permits: " + permits);
            semaphore.release(permits);
        }, 0, 1, timePeriod);

    }
}