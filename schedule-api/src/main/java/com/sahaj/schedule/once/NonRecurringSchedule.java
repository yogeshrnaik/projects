package com.sahaj.schedule.once;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import com.sahaj.schedule.AbstractSchedule;
import com.sahaj.schedule.BoundedSchedule;

public class NonRecurringSchedule extends AbstractSchedule implements BoundedSchedule {

    public NonRecurringSchedule(String eventName, LocalDate scheduledDate, LocalTime time) {
        super(eventName, scheduledDate, time);
    }

    @Override
    public LocalDateTime endDate() {
        return startDate();
    }

    @Override
    public List<LocalDateTime> getOccurrences(int limitNumberOfOccurences) {
        return Arrays.asList(scheduleStartDateTime);
    }

    @Override
    public List<LocalDateTime> getAllOccurrences() {
        return getOccurrences(1);
    }

    @Override
    public int getNumberOfOccurences() {
        return 1;
    }

    @Override
    protected LocalDateTime getFirstOccurrenceFrom(LocalDateTime fromDate) {
        return (isFromDateBeforeScheduleStartDate(fromDate) || isFromDateEqualsScheduleStartDate(fromDate))
            ? scheduleStartDateTime
            : null;
    }

    @Override
    protected LocalDateTime getNextOccurrenceAfter(LocalDateTime currDate) {
        return isFromDateBeforeScheduleStartDate(currDate) ? scheduleStartDateTime : null;
    }
}
