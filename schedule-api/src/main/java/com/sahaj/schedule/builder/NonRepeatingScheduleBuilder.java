package com.sahaj.schedule.builder;

import java.time.LocalDateTime;

import com.sahaj.schedule.BoundedSchedule;
import com.sahaj.schedule.NonRepeatingSchedule;

public class NonRepeatingScheduleBuilder extends ScheduleBuilder {

    private Integer dateOfMonth;
    private Integer month;
    private Integer year;

    public NonRepeatingScheduleBuilder(String eventName) {
        super(eventName);
    }

    public BoundedSchedule on(LocalDateTime scheduledDate) {
        return new NonRepeatingSchedule(eventName, scheduledDate);
    }

    public NonRepeatingScheduleBuilder date(int date) {
        this.dateOfMonth = date;
        return this;
    }

    public NonRepeatingScheduleBuilder month(int month) {
        this.month = month;
        return this;
    }

    public BoundedSchedule year(int year) {
        this.year = year;
        validate();

        return new NonRepeatingSchedule(this.eventName, getScheduledDate());
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
