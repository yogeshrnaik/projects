package com.sahaj.schedule;

import java.time.LocalDate;

public abstract class AbstractSchedule implements Schedule {

    protected final String eventName;
    protected LocalDate firstOccurrence;

    public AbstractSchedule(String eventName) {
        this.eventName = eventName;
    }

    protected abstract LocalDate getFirstOccurrenceFrom(LocalDate fromDate);

    public final String getEventName() {
        return eventName;
    }
}
