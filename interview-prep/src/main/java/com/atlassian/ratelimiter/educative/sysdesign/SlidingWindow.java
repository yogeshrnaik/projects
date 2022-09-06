package com.atlassian.ratelimiter.educative.sysdesign;

import java.util.ArrayList;
import java.util.List;

public class SlidingWindow implements RateLimiter {

    private List<Long> lastRequestedTimes;
    private int maxRequestsAllowed;
    private int refreshRateInMills;

    public SlidingWindow(int maxRequestsAllowed, int refreshRateInMills) {
        this.maxRequestsAllowed = maxRequestsAllowed;
        this.refreshRateInMills = refreshRateInMills;
        lastRequestedTimes = new ArrayList<>();
    }

    @Override
    public synchronized boolean isAllowed() {
        lastRequestedTimes.removeIf(time -> (System.currentTimeMillis() - time) > refreshRateInMills);
        if (lastRequestedTimes.size() >= maxRequestsAllowed) {
            return false;
        }
        lastRequestedTimes.add(System.currentTimeMillis());
        return true;
    }
}
