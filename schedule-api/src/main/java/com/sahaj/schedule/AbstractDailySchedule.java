package com.sahaj.schedule;

import java.time.LocalDateTime;
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
        if (startDate.isBefore(this.scheduleStartDate) || limitNumberOfOccurences <= 0) {
            throw new IllegalArgumentException(
                "Start date must be greater than or equal to Schedule start date and "
                    + "Number of occurences must be positive integer greater than zero.");
        }
        return LongStream.range(0, limitNumberOfOccurences)
            .mapToObj(i -> startDate.plusDays(i))
            .collect(Collectors.toList());
    }
}
