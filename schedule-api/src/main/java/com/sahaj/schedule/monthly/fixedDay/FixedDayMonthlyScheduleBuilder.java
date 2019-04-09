package com.sahaj.schedule.monthly.fixedDay;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import com.sahaj.schedule.BoundedSchedule;
import com.sahaj.schedule.Schedule;

public class FixedDayMonthlyScheduleBuilder {

    public static final String DATE_OF_MONTH_IS_INVALID = "Day of month must be <= 31";
    public static final String TIME_IS_MANDATORY = "Time is mandatory";
    public static final String START_DATE_IS_MANDATORY = "Start date is mandatory";
    public static final String ORDINAL_IS_MANDATORY = "Ordinal is mandatory";
    public static final String DAY_OF_WEEK_IS_MANDATORY = "Day of week is mandatory";

    protected String eventName;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected LocalTime time;
    protected Ordinal ordinal;
    protected DayOfWeek dayOfWeek;

    public FixedDayMonthlyScheduleBuilder(String eventName, Ordinal ordinal, DayOfWeek dayOfWeek) {
        this.eventName = eventName;
        this.ordinal = ordinal;
        this.dayOfWeek = dayOfWeek;
    }

    public FixedDayMonthlyScheduleBuilder startingOn(LocalDate start, LocalTime time) {
        this.startDate = start;
        this.time = time;
        return this;
    }

    public BoundedSchedule endingOn(LocalDate end) {
        this.endDate = end;
        validate();
        return new FixedDayMonthlyBoundedSchedule(eventName, startDate, time, ordinal, dayOfWeek, end);
    }

    public Schedule neverEnding() {
        validate();
        return new FixedDayMonthlyUnboundedSchedule(eventName, startDate, time, ordinal, dayOfWeek);
    }

    protected void validate() {
        if (startDate == null) {
            throw new IllegalStateException(START_DATE_IS_MANDATORY);
        }
        if (time == null) {
            throw new IllegalStateException(TIME_IS_MANDATORY);
        }
        if (ordinal == null) {
            throw new IllegalStateException(ORDINAL_IS_MANDATORY);
        }
        if (dayOfWeek == null) {
            throw new IllegalStateException(DAY_OF_WEEK_IS_MANDATORY);
        }
    }

}
