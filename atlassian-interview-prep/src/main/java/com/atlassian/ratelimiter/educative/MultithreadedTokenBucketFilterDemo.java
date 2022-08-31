package com.atlassian.ratelimiter.educative;

import java.util.HashSet;
import java.util.Set;

class MultithreadedTokenBucketFilterDemo {
    public static void main(String args[]) throws InterruptedException {
        Set<Thread> allThreads = new HashSet<Thread>();
        final MultithreadedTokenBucketFilter tokenBucketFilter = new MultithreadedTokenBucketFilter(1);

        for (int i = 0; i < 10; i++) {

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

class MultithreadedTokenBucketFilter {
    private long possibleTokens = 0;
    private final int MAX_TOKENS;
    private final int ONE_SECOND = 1000;

    public MultithreadedTokenBucketFilter(int maxTokens) {

        MAX_TOKENS = maxTokens;

        // Never start a thread in a constructor
        Thread dt = new Thread(() -> {
            daemonThread();
        });
        dt.setDaemon(true);
        dt.start();
    }

    private void daemonThread() {

        while (true) {

            synchronized (this) {
                if (possibleTokens < MAX_TOKENS) {
                    possibleTokens++;
                }
                this.notify();
            }

            try {
                Thread.sleep(ONE_SECOND);
            } catch (InterruptedException ie) {
                // swallow exception
            }
        }
    }

    void getToken() throws InterruptedException {

        synchronized (this) {
            while (possibleTokens == 0) {
                this.wait();
            }
            possibleTokens--;
        }

        System.out.println(
                "Granting " + Thread.currentThread().getName() + " token at " + System.currentTimeMillis() / 1000);
    }
}