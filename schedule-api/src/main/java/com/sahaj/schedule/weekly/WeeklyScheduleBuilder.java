package com.sahaj.schedule.weekly;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

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

    public WeeklyUnboundedSchedule neverEnding() {
        return new WeeklyUnboundedSchedule(eventName, startDate, time, daysOfWeek);
    }

}
