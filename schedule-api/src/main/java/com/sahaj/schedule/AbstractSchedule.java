package com.sahaj.schedule;

import java.time.LocalDateTime;

public abstract class AbstractSchedule implements Schedule {

    private final String eventName;
    protected final LocalDateTime scheduleStartDate;

    public AbstractSchedule(String eventName, LocalDateTime startDate) {
        this.eventName = eventName;
        this.scheduleStartDate = startDate;
    }

    @Override
    public String getEventName() {
        return eventName;
    }

    @Override
    public LocalDateTime startDate() {
        return scheduleStartDate;
    }
}
