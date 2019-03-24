package com.sahaj.schedule.builder;

import java.time.LocalDateTime;

import com.sahaj.schedule.BoundedSchedule;
import com.sahaj.schedule.DailyBoundedSchedule;
import com.sahaj.schedule.Schedule;

public class DailyScheduleBuilder extends ScheduleBuilder {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public DailyScheduleBuilder(String eventName) {
        super(eventName);
    }

    public DailyScheduleBuilder startingOn(LocalDateTime start) {
        this.startDate = start;
        return this;
    }

    public BoundedSchedule endingOn(LocalDateTime end) {
        this.endDate = end;

        validate();
        return new DailyBoundedSchedule(eventName, startDate, endDate);
    }

    private void validate() {
        if (startDate == null) {
            throw new IllegalStateException("Start date is mandatory");
        }
    }

    public Schedule neverEnding() {
        validate();
        return new DailyBoundedSchedule(eventName, startDate, null);
    }
}
