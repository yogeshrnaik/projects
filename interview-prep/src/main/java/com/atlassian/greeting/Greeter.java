package com.atlassian.greeting;

public class Greeter {

    public static String getGreeting(String name) {
        return String.format("Hello %s!", name);
    }
}
