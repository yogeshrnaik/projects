package com.sahaj.schedule.daily;

import java.time.LocalDateTime;
import java.util.List;

public class DailyUnboundedSchedule extends AbstractDailySchedule {

    public DailyUnboundedSchedule(String eventName, LocalDateTime startDate) {
        super(eventName, startDate);
    }

    @Override
    public List<LocalDateTime> getOccurrences(int limitNumberOfOccurences) {
        return getOccurrencesFrom(scheduleStartDate, limitNumberOfOccurences);
    }

    @Override
    protected LocalDateTime getNextOccurrenceAfter(LocalDateTime currDate) {
        return currDate.plusDays(1);
    }
}
