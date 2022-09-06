package com.atlassian.priorityqueue;

import java.util.PriorityQueue;

public class PriorityQueueExample {
    public static void main(String[] args) {
//        example1();
        exampleWithReverseOrderComparator();
    }

    private static void example1() {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        queue.add(10);
        queue.add(40);
        queue.add(30);
        queue.add(20);

        /*
            Prints:
            10
            20
            30
            40
            null
         */
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }

    private static void exampleWithReverseOrderComparator() {
        PriorityQueue<Integer> queue = new PriorityQueue<>((i, j) -> j - i);
        queue.add(10);
        queue.add(40);
        queue.add(30);
        queue.add(20);

        /*
            Prints:
            40
            30
            20
            10
            null
         */
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }
}
