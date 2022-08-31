package com.atlassian.ratelimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {
    public static void main(String[] args) throws InterruptedException {
        UserBucketCreator userBucketCreator = new UserBucketCreator(1);
        for (int i = 0; i < 12; i++) {
            userBucketCreator.accessApplication(1);
        }
        Thread.sleep(2000);
        for (int i = 0; i < 12; i++) {
            userBucketCreator.accessApplication(1);
        }
    }
}