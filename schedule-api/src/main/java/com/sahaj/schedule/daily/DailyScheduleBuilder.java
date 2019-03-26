package com.sahaj.schedule.daily;

import java.time.LocalDate;
import java.time.LocalTime;

import com.sahaj.schedule.BoundedSchedule;
import com.sahaj.schedule.Schedule;

public class DailyScheduleBuilder {

    private String eventName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime time;

    public DailyScheduleBuilder(String eventName) {
        this.eventName = eventName;
    }

    public DailyScheduleBuilder startingOn(LocalDate start, LocalTime time) {
        this.startDate = start;
        this.time = time;
        return this;
    }

    public BoundedSchedule endingOn(LocalDate end) {
        this.endDate = end;
        validate();
        return new DailyBoundedSchedule(eventName, startDate, endDate, time);
    }

    private void validate() {
        if (startDate == null) {
            throw new IllegalStateException("Start date is mandatory");
        }
        if (time == null) {
            throw new IllegalStateException("Time is mandatory");
        }
    }

    public Schedule neverEnding() {
        validate();
        return new DailyUnboundedSchedule(eventName, startDate, time);
    }
}
