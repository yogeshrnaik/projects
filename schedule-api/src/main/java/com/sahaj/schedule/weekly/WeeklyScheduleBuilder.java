package com.sahaj.schedule.weekly;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import com.sahaj.schedule.BoundedSchedule;
import com.sahaj.schedule.Schedule;

public class WeeklyScheduleBuilder {

    public static final String DAY_OF_WEEK_IS_MANDATORY = "Day of week is mandatory";
    public static final String TIME_IS_MANDATORY = "Time is mandatory";
    public static final String START_DATE_IS_MANDATORY = "Start date is mandatory";
    protected String eventName;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected LocalTime time;
    protected Set<DayOfWeek> daysOfWeek;

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

    protected void validate() {
        if (startDate == null) {
            throw new IllegalStateException(START_DATE_IS_MANDATORY);
        }
        if (time == null) {
            throw new IllegalStateException(TIME_IS_MANDATORY);
        }
        if (daysOfWeek == null || daysOfWeek.isEmpty()) {
            throw new IllegalStateException(DAY_OF_WEEK_IS_MANDATORY);
        }
    }

    public Schedule neverEnding() {
        validate();
        return new WeeklyUnboundedSchedule(eventName, startDate, time, daysOfWeek);
    }

}
