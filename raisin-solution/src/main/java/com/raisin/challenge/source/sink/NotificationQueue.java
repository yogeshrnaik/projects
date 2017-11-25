package com.raisin.challenge.source.sink;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class NotificationQueue {

    final private BlockingQueue<String> queue;

    public NotificationQueue() {
        queue = new ArrayBlockingQueue<String>(1);
    }

    public void add(String object) {
        try {
            queue.put(object);
        } catch (InterruptedException e) {
        }
    }

    public String next() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
        }
        return null;
    }

}
