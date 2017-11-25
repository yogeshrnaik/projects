package com.raisin.challenge.sink;

import static com.raisin.challenge.util.ThreadUtil.notifyOthers;
import static com.raisin.challenge.util.Util.isConnectionClosed;

import org.apache.log4j.Logger;

import com.raisin.challenge.exception.NotAcceptableException;
import com.raisin.challenge.util.ThreadUtil;

public class DataSinker implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(DataSinker.class);

    private final int sinkId;
    private final SinkWriter sinkWriter;
    private final SinkData sinkData;
    private final SinkMessageQueue sinkQueue;
    private final Object lock;

    public DataSinker(int sinkId, SinkWriter sinkWriter, SinkMessageQueue sinkQueue, SinkData sinkData, Object lock) {
        this.sinkId = sinkId;
        this.sinkWriter = sinkWriter;
        this.sinkQueue = sinkQueue;
        this.sinkData = sinkData;
        this.lock = lock;
    }

    @Override
    public void run() {
        Thread.currentThread().setName("DataSinker_" + sinkId);
        processUntilNotAllSourcesDone();
        notifyOthers(lock);
        LOGGER.info("Data sinker finished.");
    }

    private void processUntilNotAllSourcesDone() {
        while (sinkData.notAllDataProcessed()) {
            if (!processMessage(takeFromQueue())) {
                return;
            }
        }
    }

    private boolean processMessage(SinkMessage msg) {
        try {
            sink(msg);
        } catch (NotAcceptableException e) {
            ThreadUtil.notifyOthersAndWaitTillNotified(lock);
            addToQueue(msg);
        } catch (Throwable t) {
            if (isConnectionClosed(t))
                return false;
            addToQueue(msg);
        }
        return true;
    }

    private void sink(SinkMessage msg) {
        sinkWriter.write(msg.getId(), msg.getRecortType());
    }

    private void addToQueue(SinkMessage msg) {
        sinkQueue.add(msg);
    }

    private SinkMessage takeFromQueue() {
        SinkMessage msg = sinkQueue.next();
        LOGGER.info("Message taken from queue: " + msg);
        return msg;
    }

}
