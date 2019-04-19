package com.sahaj.schedule.quaterly.fixedDate;

import java.time.LocalDate;
import java.time.LocalTime;

import com.sahaj.schedule.BoundedSchedule;
import com.sahaj.schedule.Schedule;

public class FixedDateQuaterlyScheduleBuilder {

    public static final String DATE_OF_MONTH_IS_INVALID = "Day of month must be <= 31";
    public static final String TIME_IS_MANDATORY = "Time is mandatory";
    public static final String START_DATE_IS_MANDATORY = "Start date is mandatory";

    protected String eventName;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected LocalTime time;
    protected int dayOfMonth;

    public FixedDateQuaterlyScheduleBuilder(String eventName, int dayOfMonth) {
        this.eventName = eventName;
        this.dayOfMonth = dayOfMonth;
    }

    public FixedDateQuaterlyScheduleBuilder startingOn(LocalDate start, LocalTime time) {
        this.startDate = start;
        this.time = time;
        return this;
    }

    public BoundedSchedule endingOn(LocalDate end) {
        this.endDate = end;
        validate();
        return new FixedDateQuaterlyBoundedSchedule(eventName, startDate, time, dayOfMonth, endDate);
    }

    public Schedule neverEnding() {
        validate();
        return new FixedDateQuaterlyUnboundedSchedule(eventName, startDate, time, dayOfMonth);
    }

    protected void validate() {
        if (startDate == null) {
            throw new IllegalStateException(START_DATE_IS_MANDATORY);
        }
        if (time == null) {
            throw new IllegalStateException(TIME_IS_MANDATORY);
        }
        if (dayOfMonth > 31) {
            throw new IllegalStateException(DATE_OF_MONTH_IS_INVALID);
        }
    }
}
