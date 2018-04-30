package com.tomtom.cleancode.examples;

public class Mutiplication {

    public static long multiply(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        if (a == 1 || b == 1) {
            return a == 1 ? b : a;
        }
        int result = 0;
        for (int i = 1; i <= b; i++) {
            result += a;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("1 * 0 = " + multiply(1, 0));
        System.out.println("0 * 1 = " + multiply(0, 1));
        System.out.println("10 * 5 = " + multiply(10, 5));
        System.out.println("-10 * 5 = " + multiply(-10, 5));
        System.out.println("-10 * -5 = " + multiply(-10, -5));
    }
}
