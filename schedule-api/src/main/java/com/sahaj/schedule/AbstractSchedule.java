package com.sahaj.schedule;

public abstract class AbstractSchedule implements Schedule {

    private final String eventName;

    public AbstractSchedule(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
