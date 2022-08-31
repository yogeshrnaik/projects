package com.atlassian.ratelimiter.educative;

import java.util.HashSet;
import java.util.Set;

public class RateLimiterDemonstration {
    public static void main(String args[]) throws InterruptedException {
        final TokenBucketFilter tokenBucketFilter = new TokenBucketFilter(5);
        TokenBucketFilter.runTestMaxTokenIs1(tokenBucketFilter);
        System.out.println("==================");
        Thread.sleep(2000);
        TokenBucketFilter.runTestMaxTokenIs1(tokenBucketFilter);
//        TokenBucketFilter.runTestMaxTokenIsTen();
    }
}

class TokenBucketFilter {

    private int MAX_TOKENS;
    private long lastRequestTime = System.currentTimeMillis();
    long possibleTokens = 0;

    public TokenBucketFilter(int maxTokens) {
        MAX_TOKENS = maxTokens;
        possibleTokens = maxTokens;
    }

    synchronized boolean getToken() throws InterruptedException {

        possibleTokens += (System.currentTimeMillis() - lastRequestTime) / 1000;

        if (possibleTokens > MAX_TOKENS) {
            possibleTokens = MAX_TOKENS;
        }

        if (possibleTokens == 0) {
            System.out.println("Not Granting " + Thread.currentThread().getName() + " token at " + (System.currentTimeMillis() / 1000));
            return false;
        } else {
            possibleTokens--;
        }
        lastRequestTime = System.currentTimeMillis();

        System.out.println("Granting " + Thread.currentThread().getName() + " token at " + (System.currentTimeMillis() / 1000));
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