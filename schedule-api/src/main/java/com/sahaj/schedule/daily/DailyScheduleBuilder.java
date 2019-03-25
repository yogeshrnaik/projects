package com.sahaj.schedule.daily;

import java.time.LocalDateTime;

import com.sahaj.schedule.BoundedSchedule;
import com.sahaj.schedule.Schedule;

public class DailyScheduleBuilder {

    private String eventName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public DailyScheduleBuilder(String eventName) {
        this.eventName = eventName;
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
        return new DailyUnboundedSchedule(eventName, startDate);
    }
}
