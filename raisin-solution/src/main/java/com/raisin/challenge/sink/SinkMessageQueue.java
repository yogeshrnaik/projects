package com.raisin.challenge.sink;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SinkMessageQueue {

    final private BlockingQueue<SinkMessage> queue;

    public SinkMessageQueue() {
        queue = new LinkedBlockingQueue<>();
    }

    public void add(SinkMessage object) {
        try {
            queue.put(object);
        } catch (InterruptedException e) {
        }
    }

    public SinkMessage next() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
        }
        return null;
    }

}
