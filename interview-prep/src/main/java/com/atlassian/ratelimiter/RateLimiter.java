package com.atlassian.ratelimiter;

public interface RateLimiter {
    boolean grantAccess();
}
