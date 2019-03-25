package com.sahaj.schedule.once;

import java.time.LocalDateTime;

import com.sahaj.schedule.BoundedSchedule;

public class NonRecurringScheduleBuilder {

    private final String eventName;
    private Integer dateOfMonth;
    private Integer month;
    private Integer year;

    public NonRecurringScheduleBuilder(String eventName) {
        this.eventName = eventName;
    }

    public BoundedSchedule on(LocalDateTime scheduledDate) {
        return new NonRecurringSchedule(eventName, scheduledDate);
    }

    public NonRecurringScheduleBuilder date(int date) {
        this.dateOfMonth = date;
        return this;
    }

    public NonRecurringScheduleBuilder month(int month) {
        this.month = month;
        return this;
    }

    public BoundedSchedule year(int year) {
        this.year = year;
        validate();

        return new NonRecurringSchedule(this.eventName, getScheduledDate());
    }

    private LocalDateTime getScheduledDate() {
        return LocalDateTime.of(year, month, dateOfMonth, 0, 0);
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
