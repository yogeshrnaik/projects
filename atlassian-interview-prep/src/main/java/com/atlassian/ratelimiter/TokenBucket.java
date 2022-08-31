package com.atlassian.ratelimiter;

public class TokenBucket implements RateLimiter {
    @Override
    public boolean grantAccess() {
        return false;
    }
}
