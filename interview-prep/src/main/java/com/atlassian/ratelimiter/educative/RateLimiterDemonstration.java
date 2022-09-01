package com.atlassian.ratelimiter.educative;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RateLimiterDemonstration {
    public static void main(String args[]) throws InterruptedException {
        final TokenBucketFilter tokenBucketFilter = new TokenBucketFilter(1, 100, TimeUnit.MILLISECONDS);
        tokenBucketFilter.getToken();
        tokenBucketFilter.getToken();
        tokenBucketFilter.getToken();
        System.out.println("Waiting 100 ms ==================");
        Thread.sleep(100);
        tokenBucketFilter.getToken();
        tokenBucketFilter.getToken();
        tokenBucketFilter.getToken();
        tokenBucketFilter.getToken();
        System.out.println("Waiting 100 ms ==================");
        Thread.sleep(100);
        tokenBucketFilter.getToken();
        tokenBucketFilter.getToken();
        tokenBucketFilter.getToken();
        tokenBucketFilter.getToken();
        tokenBucketFilter.getToken();
//        TokenBucketFilter.runTestMaxTokenIs1(tokenBucketFilter);
        System.out.println("==================");
//        Thread.sleep(4000);
//        TokenBucketFilter.runTestMaxTokenIs1(tokenBucketFilter);
//        TokenBucketFilter.runTestMaxTokenIsTen();
    }
}

class TokenBucketFilter {

    private int MAX_TOKENS;
    private long lastRequestTime = System.currentTimeMillis();
    long possibleTokens = 0;
    int timeInterval;
    TimeUnit timeUnit;

    public TokenBucketFilter(int maxTokens, int timeInterval, TimeUnit timeUnit) {
        MAX_TOKENS = maxTokens;
        possibleTokens = maxTokens;
        this.timeInterval = timeInterval;
        this.timeUnit = timeUnit;
    }

    private int getTimeWindowInMilliseconds() {
        if (timeUnit == TimeUnit.SECONDS)
            return this.timeInterval * 1000;

        if (timeUnit == TimeUnit.MILLISECONDS)
            return this.timeInterval;

        if (timeUnit == TimeUnit.NANOSECONDS)
            return this.timeInterval / 1000;

        return timeInterval;
    }

    synchronized boolean getToken() throws InterruptedException {

        long newTokens = (System.currentTimeMillis() - lastRequestTime) / getTimeWindowInMilliseconds();
        this.possibleTokens += newTokens;

        if (this.possibleTokens > MAX_TOKENS) {
            this.possibleTokens = MAX_TOKENS;
        }

        if (this.possibleTokens == 0) {
            System.out.println("Not Granting " + Thread.currentThread().getName() + " token at " + System.currentTimeMillis());
            return false;
        } else {
            this.possibleTokens--;
        }
        lastRequestTime = System.currentTimeMillis();

        System.out.println("Granting " + Thread.currentThread().getName() + " token at " + System.currentTimeMillis() + ", newTokens: " + newTokens);
        return true;
    }

    public static void runTestMaxTokenIs1(TokenBucketFilter tokenBucketFilter) throws InterruptedException {
        Set<Thread> allThreads = new HashSet<Thread>();
        for (int i = 0; i < 10; i++) {
            Thread thread = createThread(tokenBucketFilter, i);
            allThreads.add(thread);
        }

        for (Thread t : allThreads) {
            t.start();
        }

        for (Thread t : allThreads) {
            t.join();
        }
    }

    private static Thread createThread(TokenBucketFilter tokenBucketFilter, int i) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    tokenBucketFilter.getToken();
                } catch (InterruptedException ie) {
                    System.out.println("We have a problem");
                }
            }
        });
        thread.setName("Thread_" + (i + 1));
        return thread;
    }

    public static void runTestMaxTokenIsTen(TokenBucketFilter tokenBucketFilter) throws InterruptedException {

        Set<Thread> allThreads = new HashSet<Thread>();

        // Sleep for 10 seconds.
        Thread.sleep(10000);

        // Generate 12 threads requesting tokens almost all at once.
        for (int i = 0; i < 12; i++) {
            Thread thread = createThread(tokenBucketFilter, i);
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