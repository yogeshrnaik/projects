package com.sahaj.schedule.once;

import java.time.LocalDate;
import java.time.LocalTime;

import com.sahaj.schedule.BoundedSchedule;

public class NonRecurringScheduleBuilder {

    private final String eventName;
    private Integer dateOfMonth;
    private Integer month;
    private Integer year;
    private LocalTime time;

    public NonRecurringScheduleBuilder(String eventName) {
        this.eventName = eventName;
    }

    public BoundedSchedule on(LocalDate scheduledDate, LocalTime time) {
        return new NonRecurringSchedule(eventName, scheduledDate, time);
    }

    public NonRecurringScheduleBuilder date(int date) {
        this.dateOfMonth = date;
        return this;
    }

    public NonRecurringScheduleBuilder month(int month) {
        this.month = month;
        return this;
    }

    public NonRecurringScheduleBuilder year(int year) {
        this.year = year;
        return this;
    }

    public BoundedSchedule at(LocalTime time) {
        this.time = time;
        validate();

        return new NonRecurringSchedule(this.eventName, getScheduledDate(), time);
    }

    private LocalDate getScheduledDate() {
        return LocalDate.of(year, month, dateOfMonth);
    }

    private void validate() {
        if (dateOfMonth == null) {
            throw new IllegalStateException("Date of month is mandatory");
        }
        if (month == null) {
            throw new IllegalStateException("Month is mandatory");
        }
        if (year == null) {
            throw new IllegalStateException("Year is mandatory");
        }
    }

}
