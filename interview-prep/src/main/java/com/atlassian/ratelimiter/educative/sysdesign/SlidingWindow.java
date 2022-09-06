package com.atlassian.ratelimiter.educative.sysdesign;

import java.util.ArrayList;
import java.util.List;

public class SlidingWindow implements RateLimiter {

    private List<Long> lastRequestedTimes;
    private int maxRequestsAllowed;
    private int timeWindowInMillis;

    public SlidingWindow(int maxRequestsAllowed, int timeWindowInMillis) {
        this.maxRequestsAllowed = maxRequestsAllowed;
        this.timeWindowInMillis = timeWindowInMillis;
        lastRequestedTimes = new ArrayList<>();
    }

    @Override
    public synchronized boolean isAllowed() {
        lastRequestedTimes.removeIf(time -> (System.currentTimeMillis() - time) > timeWindowInMillis);
        if (lastRequestedTimes.size() >= maxRequestsAllowed) {
            return false;
        }
        lastRequestedTimes.add(System.currentTimeMillis());
        return true;
    }
}
