package com.sahaj.schedule.daily;

import java.time.LocalDateTime;

import com.sahaj.schedule.AbstractSchedule;

public abstract class AbstractDailySchedule extends AbstractSchedule {

    public AbstractDailySchedule(String eventName, LocalDateTime startDate) {
        super(eventName, startDate);
    }

    protected LocalDateTime getFirstOccurrenceFrom(LocalDateTime fromDate) {
        if (isFromDateBeforeScheduleStartDate(fromDate)) {
            return scheduleStartDate;
        }

        if (fromDate.toLocalTime().isAfter(scheduleStartDate.toLocalTime())) {
            return LocalDateTime.of(fromDate.toLocalDate().plusDays(1), scheduleStartDate.toLocalTime());
        }
        return LocalDateTime.of(fromDate.toLocalDate(), scheduleStartDate.toLocalTime());

        // if time in fromDate is after the eventTime
        // firstOccurrence = fromDate+1 day with eventTime
        // else if time in fromDate is before the eventTime
        // firstOccurrence = fromDate with eventTime
    }
}
