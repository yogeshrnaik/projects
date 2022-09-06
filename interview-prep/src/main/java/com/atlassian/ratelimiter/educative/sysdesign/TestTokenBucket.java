package com.atlassian.ratelimiter.educative.sysdesign;

import org.junit.Assert;
import org.junit.Test;

public class TestTokenBucket {

    @Test
    public void test10TokensPerSecond() throws InterruptedException {
        RateLimiter rateLimiter = new TokenBucket(10, 1000);
        assertRateLimitAllowedXTimes(rateLimiter, 10);
        Assert.assertFalse(rateLimiter.isAllowed());
        Thread.sleep(1000);
        assertRateLimitAllowedXTimes(rateLimiter, 10);
        Assert.assertFalse(rateLimiter.isAllowed());

    }

    @Test
    public void test5TokensPer100Milliseconds() throws InterruptedException {
        RateLimiter rateLimiter = new TokenBucket(5, 100);
        assertRateLimitAllowedXTimes(rateLimiter, 5);
        Assert.assertFalse(rateLimiter.isAllowed());
        Thread.sleep(100);
        assertRateLimitAllowedXTimes(rateLimiter, 5);
        Assert.assertFalse(rateLimiter.isAllowed());

    }

    private void assertRateLimitAllowedXTimes(RateLimiter rateLimiter, int maxTokens) {
        for (int i = 0; i < maxTokens; i++) {
            Assert.assertTrue(rateLimiter.isAllowed());
        }
    }
}
