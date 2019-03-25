package com.sahaj.schedule.once;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.sahaj.schedule.AbstractSchedule;
import com.sahaj.schedule.BoundedSchedule;

public class NonRecurringSchedule extends AbstractSchedule implements BoundedSchedule {

    private final LocalDateTime scheduledDate;

    public NonRecurringSchedule(String eventName, LocalDateTime scheduledDate) {
        super(eventName);
        this.scheduledDate = scheduledDate;
    }

    @Override
    public LocalDateTime startDate() {
        return scheduledDate;
    }

    @Override
    public LocalDateTime endDate() {
        return scheduledDate;
    }

    @Override
    public List<LocalDateTime> getOccurrences(int limitNumberOfOccurences) {
        return Arrays.asList(scheduledDate);
    }

    @Override
    public List<LocalDateTime> getOccurrencesFrom(LocalDateTime startDate, int numberOfOccurences) {
        return (startDate.isBefore(scheduledDate) || startDate.equals(scheduledDate))
            ? getOccurrences(numberOfOccurences)
            : Collections.emptyList();
    }

    @Override
    public List<LocalDateTime> getAllOccurrences() {
        return getOccurrences(1);
    }

    @Override
    public int getNumberOfOccurences() {
        return 1;
    }
}
