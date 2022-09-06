package com.atlassian.ratelimiter.youtube;

public interface RateLimiter {
    boolean grantAccess();
}
