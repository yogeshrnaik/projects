package com.sahaj.schedule.builder;

import java.time.Month;
import java.util.Date;
import java.util.GregorianCalendar;

import com.sahaj.schedule.NonRepeatingSchedule;
import com.sahaj.schedule.Schedule;

public class NonRepeatingScheduleBuilder {

    private String eventName;
    private Integer dateOfMonth;
    private Month month;
    private Integer year;

    public NonRepeatingScheduleBuilder(String eventName) {
        this.eventName = eventName;
    }

    public NonRepeatingSchedule on(Date scheduledDate) {
        return new NonRepeatingSchedule(eventName, scheduledDate);
    }

    public NonRepeatingScheduleBuilder date(int date) {
        this.dateOfMonth = date;
        return this;
    }

    public NonRepeatingScheduleBuilder month(Month month) {
        this.month = month;
        return this;
    }

    public Schedule year(int year) {
        this.year = year;
        validate();

        return new NonRepeatingSchedule(this.eventName, getScheduledDate());
    }

    private Date getScheduledDate() {
        return new GregorianCalendar(year, month.ordinal(), dateOfMonth).getTime();
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
