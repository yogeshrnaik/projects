package com.sahaj.schedule.daily;

import java.time.LocalDateTime;
import java.util.List;

public class DailyUnboundedSchedule extends AbstractDailySchedule {

    public DailyUnboundedSchedule(String eventName, LocalDateTime startDate) {
        super(eventName, startDate);
    }

    @Override
    public List<LocalDateTime> getOccurrences(int limitNumberOfOccurences) {
        return getLimitedOccurrencesFrom(scheduleStartDate, limitNumberOfOccurences);
    }

    @Override
    public List<LocalDateTime> getOccurrencesFrom(LocalDateTime startDate, int numberOfOccurences) {
        return getLimitedOccurrencesFrom(startDate, numberOfOccurences);
    }
}
