package com.sahaj.schedule;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public abstract class AbstractDailySchedule extends AbstractSchedule {

    protected final LocalDateTime scheduleStartDate;

    public AbstractDailySchedule(String eventName, LocalDateTime startDate) {
        super(eventName);
        this.scheduleStartDate = startDate;
    }

    @Override
    public LocalDateTime startDate() {
        return scheduleStartDate;
    }

    @Override
    public List<LocalDateTime> getOccurrences(int limitNumberOfOccurences) {
        return getOccurrencesFrom(scheduleStartDate, limitNumberOfOccurences);
    }

    protected List<LocalDateTime> getLimitedOccurrencesFrom(LocalDateTime startDate, int limitNumberOfOccurences) {
        if (limitNumberOfOccurences <= 0) {
            return Collections.emptyList();
        }
        final LocalDateTime actualStartDate = startDate.isBefore(this.scheduleStartDate) ? scheduleStartDate : startDate;
        return LongStream.range(0, limitNumberOfOccurences)
            .mapToObj(i -> actualStartDate.plusDays(i))
            .collect(Collectors.toList());
    }
}
