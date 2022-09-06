package com.atlassian.ratelimiter.educative.sysdesign;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class TestTokenBucket {

    @Test
    public void test10TokensPerSecond() throws InterruptedException {
        RateLimiter rateLimiter = new TokenBucket(10, 1000);
        assertRateLimiterAllowsXRequests(rateLimiter, 10);
        Assert.assertFalse(rateLimiter.isAllowed());
        Thread.sleep(1000);
        assertRateLimiterAllowsXRequests(rateLimiter, 10);
        Assert.assertFalse(rateLimiter.isAllowed());

    }

    @Test
    public void test5TokensPer100Milliseconds() throws InterruptedException {
        RateLimiter rateLimiter = new TokenBucket(5, 100);
        assertRateLimiterAllowsXRequests(rateLimiter, 5);
        Assert.assertFalse(rateLimiter.isAllowed());
        Thread.sleep(100);
        assertRateLimiterAllowsXRequests(rateLimiter, 5);
        Assert.assertFalse(rateLimiter.isAllowed());

    }

    private void assertRateLimiterAllowsXRequests(RateLimiter rateLimiter, int numOfRequests) throws InterruptedException {
//        assertMultithreadedRateLimitAllowedXTimes(rateLimiter, numOfRequests);
        for (int i = 0; i < numOfRequests; i++) {
            Assert.assertTrue(rateLimiter.isAllowed());
        }
    }

    private void assertMultithreadedRateLimitAllowsXRequests(RateLimiter rateLimiter, int numOfRequests) throws InterruptedException {
        Set<Thread> allThreads = new HashSet<Thread>();
        for (int i = 0; i < numOfRequests; i++) {
            Thread thread = new Thread(() -> Assert.assertTrue(rateLimiter.isAllowed()));
            thread.setName("Thread_" + (i + 1));
            allThreads.add(thread);
        }

        for (Thread t : allThreads) {
            t.start();
        }

        for (Thread t : allThreads) {
            t.join();
        }
    }
}
