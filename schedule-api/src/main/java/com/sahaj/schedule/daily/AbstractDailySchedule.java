package com.sahaj.schedule.daily;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import com.sahaj.schedule.AbstractSchedule;

public abstract class AbstractDailySchedule extends AbstractSchedule {

    public AbstractDailySchedule(String eventName, LocalDate startDate, LocalTime time) {
        super(eventName, startDate, time);
    }

    protected Optional<LocalDateTime> getFirstOccurrenceFrom(LocalDateTime fromDate) {
        if (isBeforeScheduleStartDate(fromDate) || isSameAsScheduleStartDate(fromDate)) {
            return Optional.of(scheduleStartDateTime);
        }

        if (fromDate.toLocalTime().isAfter(scheduleTime)) {
            // if time in fromDate is after the scheduleTime
            // firstOccurrence = fromDate + 1 day with scheduleTime
            return Optional.of(LocalDateTime.of(fromDate.toLocalDate().plusDays(1), scheduleTime));
        }
        return Optional.of(LocalDateTime.of(fromDate.toLocalDate(), scheduleTime));
    }
}
