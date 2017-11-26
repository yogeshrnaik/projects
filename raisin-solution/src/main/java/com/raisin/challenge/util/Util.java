package com.raisin.challenge.util;

import org.apache.commons.lang3.time.DurationFormatUtils;

public class Util {

    public static boolean isConnectionClosed(Throwable t) {
        return (t != null && t.toString().contains("Connection refused") || t.toString().contains("Connection reset"));
    }

    public static boolean is406Error(Throwable t) {
        return t.toString().contains("406");
    }

    public static String formatTimeInHoursMinutesAndSeconds(long milliSeconds) {
        return DurationFormatUtils.formatDuration(milliSeconds, "HH 'hr', mm 'min', ss 'sec'");
    }
}
