package com.atlassian.ratelimiter.educative.sysdesign;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class TestRateLimiter {

    @Test
    public void testTokenBucket_1TokenPerSecond() throws InterruptedException {
        RateLimiter rateLimiter = new TokenBucket(1, 1, 1000);
        assertRateLimiterAllowsXRequests(rateLimiter, 1);
        Assert.assertFalse(rateLimiter.isAllowed());
        Thread.sleep(1000);
        assertRateLimiterAllowsXRequests(rateLimiter, 1);
        Assert.assertFalse(rateLimiter.isAllowed());
    }

    @Test
    public void testTokenBucket_10TokensPerSecond() throws InterruptedException {
        RateLimiter rateLimiter = new TokenBucket(10, 10, 1000);
        assertRateLimiterAllowsXRequests(rateLimiter, 10);
        Assert.assertFalse(rateLimiter.isAllowed());
        Thread.sleep(1000);
        assertRateLimiterAllowsXRequests(rateLimiter, 10);
        Assert.assertFalse(rateLimiter.isAllowed());
    }

    @Test
    public void testTokenBucket_5TokensPer100Milliseconds() throws InterruptedException {
        RateLimiter rateLimiter = new TokenBucket(5, 5, 100);
        assertRateLimiterAllowsXRequests(rateLimiter, 5);
        Assert.assertFalse(rateLimiter.isAllowed());
        Thread.sleep(100);
        assertRateLimiterAllowsXRequests(rateLimiter, 5);
        Assert.assertFalse(rateLimiter.isAllowed());

    }

    @Test
    public void testSlidingWindow_10TokensPerSecond() throws InterruptedException {
        RateLimiter rateLimiter = new SlidingWindow(10, 1000);
        assertRateLimiterAllowsXRequests(rateLimiter, 10);
        Assert.assertFalse(rateLimiter.isAllowed());
        Thread.sleep(1000);
        assertRateLimiterAllowsXRequests(rateLimiter, 10);
        Assert.assertFalse(rateLimiter.isAllowed());
    }

    @Test
    public void testSlidingWindow_5TokensPer100Milliseconds() throws InterruptedException {
        RateLimiter rateLimiter = new SlidingWindow(5, 100);
        assertRateLimiterAllowsXRequests(rateLimiter, 5);
        Assert.assertFalse(rateLimiter.isAllowed());
        Thread.sleep(100);
        assertRateLimiterAllowsXRequests(rateLimiter, 5);
        Assert.assertFalse(rateLimiter.isAllowed());

    }

    @Test
    public void testFixedWindow_10TokensPerSecond() throws InterruptedException {
        RateLimiter rateLimiter = new FixedWindow(10, 1000);
        assertRateLimiterAllowsXRequests(rateLimiter, 10);
        Assert.assertFalse(rateLimiter.isAllowed());
        Thread.sleep(1000);
        assertRateLimiterAllowsXRequests(rateLimiter, 10);
        Assert.assertFalse(rateLimiter.isAllowed());

    }

    @Test
    public void testFixedWindow_5TokensPer100Milliseconds() throws InterruptedException {
        RateLimiter rateLimiter = new FixedWindow(5, 100);
        assertRateLimiterAllowsXRequests(rateLimiter, 5);
        Assert.assertFalse(rateLimiter.isAllowed());
        Thread.sleep(100);
        assertRateLimiterAllowsXRequests(rateLimiter, 5);
        Assert.assertFalse(rateLimiter.isAllowed());

    }

    private void assertRateLimiterAllowsXRequests(RateLimiter rateLimiter, int numOfRequests) throws InterruptedException {
//        assertMultithreadedRateLimitAllowsXRequests(rateLimiter, numOfRequests);
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
