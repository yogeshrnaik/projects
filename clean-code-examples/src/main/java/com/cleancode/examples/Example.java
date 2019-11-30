package com.cleancode.examples;

import java.util.Arrays;
import java.util.List;

public class Example {

    public static void main(String[] args) {
        System.out.println(sum(Arrays.asList(10, 20)));
        System.out.println(sum(Arrays.asList(10.2, 20.2)));

        System.out.println("*********************************************");

        System.out.println(sumStream(Arrays.asList(10, 20)));
        System.out.println(sumStream(Arrays.asList(10.2, 20.2)));
    }

    public static Number sum(List<? extends Number> nums) {
        Double total = 0.0;

        for (Number n : nums) {
            total += n.doubleValue();
        }
        return total;
    }

    public static Number sumStream(List<? extends Number> nums) {
        return nums.stream()
            .mapToDouble(i -> i.doubleValue())
            .sum();
    }

}
