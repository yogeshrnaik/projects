package com.sahaj.schedule.daily;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.sahaj.schedule.AbstractSchedule;

public abstract class AbstractDailySchedule extends AbstractSchedule {

    public AbstractDailySchedule(String eventName, LocalDate startDate, LocalTime time) {
        super(eventName, startDate, time);
    }

    protected LocalDateTime getFirstOccurrenceFrom(LocalDateTime fromDate) {
        if (isFromDateBeforeScheduleStartDate(fromDate) || isFromDateEqualsScheduleStartDate(fromDate)) {
            return scheduleStartDateTime;
        }

        if (fromDate.toLocalTime().isAfter(scheduleTime)) {
            // if time in fromDate is after the scheduleTime
            // firstOccurrence = fromDate + 1 day with scheduleTime
            return LocalDateTime.of(fromDate.toLocalDate().plusDays(1), scheduleTime);
        }
        return LocalDateTime.of(fromDate.toLocalDate(), scheduleTime);
    }
}
