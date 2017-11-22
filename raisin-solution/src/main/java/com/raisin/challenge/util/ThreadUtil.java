package com.raisin.challenge.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class ThreadUtil {

    private static final Logger LOGGER = Logger.getLogger(ThreadUtil.class);

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            LOGGER.warn("Thread is Interrupted.", e);
        }
    }

    public static void awaitTermination(ExecutorService executorService) {
        executorService.shutdown();

        while (!executorService.isTerminated()) {
            try {
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
