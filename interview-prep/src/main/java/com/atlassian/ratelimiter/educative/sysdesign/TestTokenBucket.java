package com.atlassian.ratelimiter.educative.sysdesign;

import org.junit.Assert;
import org.junit.Test;

public class TestTokenBucket {

    @Test
    public void test10TokensPerSecond() throws InterruptedException {
        TokenBucket tokenBucket = new TokenBucket(10);
        assert10TokensPerSecond(tokenBucket);
        Thread.sleep(1000);
        assert10TokensPerSecond(tokenBucket);

    }

    private void assert10TokensPerSecond(TokenBucket tokenBucket) {
        for (int i = 0; i < 10; i++) {
            Assert.assertTrue(tokenBucket.isAllowed());
        }
        Assert.assertFalse(tokenBucket.isAllowed());
    }

}
