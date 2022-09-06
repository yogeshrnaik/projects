package com.atlassian.ratelimiter.educative.sysdesign;

public class SlidingWindow implements RateLimiter{
    @Override
    public boolean isAllowed() {
        return false;
    }
}
