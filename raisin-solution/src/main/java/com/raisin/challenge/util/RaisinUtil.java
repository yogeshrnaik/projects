package com.raisin.challenge.util;

import org.apache.log4j.Logger;

public class RaisinUtil {

    private static final Logger LOGGER = Logger.getLogger(RaisinUtil.class);

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            LOGGER.warn("Thread is Interrupted.", e);
        }
    }
}
