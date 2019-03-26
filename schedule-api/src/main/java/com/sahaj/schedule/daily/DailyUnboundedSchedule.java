package com.sahaj.schedule.daily;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class DailyUnboundedSchedule extends AbstractDailySchedule {

    public DailyUnboundedSchedule(String eventName, LocalDate startDate, LocalTime time) {
        super(eventName, startDate, time);
    }

    @Override
    public List<LocalDateTime> getOccurrences(int limitNumberOfOccurences) {
        return getOccurrencesFrom(scheduleStartDateTime, limitNumberOfOccurences);
    }

    @Override
    protected LocalDateTime getNextOccurrenceAfter(LocalDateTime currDate) {
        return currDate.plusDays(1);
    }
}
