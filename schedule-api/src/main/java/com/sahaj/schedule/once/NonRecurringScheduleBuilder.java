package com.sahaj.schedule.once;

import java.time.LocalDate;
import java.time.LocalTime;

import com.sahaj.schedule.BoundedSchedule;

public class NonRecurringScheduleBuilder {

    private final String eventName;

    public NonRecurringScheduleBuilder(String eventName) {
        this.eventName = eventName;
    }

    public BoundedSchedule on(LocalDate scheduledDate, LocalTime time) {
        return new NonRecurringSchedule(eventName, scheduledDate, time);
    }
}
