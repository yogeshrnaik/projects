package com.sahaj.schedule.weekly;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import com.sahaj.schedule.BoundedSchedule;

public class WeeklyScheduleBuilder {

    private String eventName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime time;
    private Set<DayOfWeek> daysOfWeek;

    public WeeklyScheduleBuilder(String eventName, Set<DayOfWeek> daysOfWeek) {
        this.eventName = eventName;
        this.daysOfWeek = daysOfWeek;
    }

    public WeeklyScheduleBuilder startingOn(LocalDate start, LocalTime time) {
        this.startDate = start;
        this.time = time;
        return this;
    }

    public BoundedSchedule endingOn(LocalDate end) {
        this.endDate = end;
        validate();
        return new WeeklyBoundedSchedule(eventName, startDate, time, daysOfWeek, endDate);
    }

    private void validate() {
        if (startDate == null) {
            throw new IllegalStateException("Start date is mandatory");
        }
        if (time == null) {
            throw new IllegalStateException("Time is mandatory");
        }
        if (daysOfWeek == null || daysOfWeek.isEmpty()) {
            throw new IllegalStateException("Day of week is mandatory");
        }
    }

    public WeeklyUnboundedSchedule neverEnding() {
        return new WeeklyUnboundedSchedule(eventName, startDate, time, daysOfWeek);
    }

}
