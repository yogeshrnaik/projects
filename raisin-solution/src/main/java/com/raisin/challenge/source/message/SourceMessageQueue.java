package com.raisin.challenge.source.message;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SourceMessageQueue {

    final private BlockingQueue<SourceMessage> queue;

    public SourceMessageQueue(int queueSize) {
        queue = new ArrayBlockingQueue<SourceMessage>(queueSize);
    }

    public void add(SourceMessage object) {
        try {
            queue.put(object);
        } catch (InterruptedException e) {
        }
    }

    public SourceMessage next() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
        }
        return null;
    }
}
