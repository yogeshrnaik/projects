package com.tomtom.cleancode.examples;

public class Mutiplication {

    public static int multiply(int a, int b) {
        int result = 0;
        for (int i = 1; i <= b; i++) {
            result += a;
        }
        return result;
    }
}
