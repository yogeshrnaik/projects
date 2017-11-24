package com.raisin.challenge.util;

public class Util {

    public static boolean isConnectionClosed(Throwable t) {
        return (t != null && t.toString().contains("Connection refused") || t.toString().contains("Connection reset"));
    }

    public static boolean is406Error(Throwable t) {
        return t.toString().contains("406");
    }
}
