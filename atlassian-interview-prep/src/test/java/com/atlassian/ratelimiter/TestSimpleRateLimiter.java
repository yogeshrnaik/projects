package com.atlassian.ratelimiter;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class TestSimpleRateLimiter {

    @Test
    public void testSimpleRateLimiter() throws InterruptedException {
        SimpleRateLimiter limiter = SimpleRateLimiter.create(10, TimeUnit.SECONDS);

        for (int i = 0; i < 100; i++) {
            Thread.sleep(50);
            System.out.println(i + " -> " + limiter.tryAcquire());
        }
    }
}
