package com.raisin.challenge.source.message;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MessageQueue {

    final private BlockingQueue<MessageDto> queue;

    public MessageQueue(int queueSize) {
        queue = new ArrayBlockingQueue<MessageDto>(queueSize);
    }

    public void add(MessageDto object) {
        try {
            queue.put(object);
        } catch (InterruptedException e) {
        }
    }

    public MessageDto next() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
        }
        return null;
    }
}
