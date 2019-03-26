package com.sahaj.schedule;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractSchedule implements Schedule {

    private final String eventName;
    protected final LocalDateTime scheduleStartDate;

    public AbstractSchedule(String eventName, LocalDateTime startDate) {
        this.eventName = eventName;
        this.scheduleStartDate = startDate;
    }

    @Override
    public String getEventName() {
        return eventName;
    }

    @Override
    public LocalDateTime startDate() {
        return scheduleStartDate;
    }

    @Override
    public List<LocalDateTime> getOccurrences(int limitNumberOfOccurences) {
        return getOccurrencesFrom(scheduleStartDate, limitNumberOfOccurences);
    }

    @Override
    public List<LocalDateTime> getOccurrencesFrom(LocalDateTime fromDate, int numberOfOccurences) {
        LocalDateTime firstOccurrence = getFirstOccurrenceFrom(fromDate);
        if (firstOccurrence == null || numberOfOccurences <= 0) {
            return Collections.emptyList();
        }

        return getNextOccurrencesFrom(firstOccurrence, numberOfOccurences);
    }

    private List<LocalDateTime> getNextOccurrencesFrom(LocalDateTime firstOccurrence, int numberOfOccurences) {
        List<LocalDateTime> occurrences = new ArrayList<>(Arrays.asList(firstOccurrence));
        LocalDateTime currOccurrence = firstOccurrence;
        int counter = 1;
        while (counter < numberOfOccurences && currOccurrence != null) {
            currOccurrence = getNextOccurrenceAfter(currOccurrence);
            if (currOccurrence != null) {
                occurrences.add(currOccurrence);
                counter++;
            }
        }
        return occurrences;
    }

    protected abstract LocalDateTime getFirstOccurrenceFrom(LocalDateTime fromDate);

    protected abstract LocalDateTime getNextOccurrenceAfter(LocalDateTime currDate);

    protected boolean isFromDateBeforeScheduleStartDate(LocalDateTime fromDate) {
        return fromDate.isBefore(scheduleStartDate);
    }

    protected boolean isFromDateEqualsScheduleStartDate(LocalDateTime fromDate) {
        return fromDate.equals(scheduleStartDate);
    }

    protected boolean isFromDateAfterScheduleStartDate(LocalDateTime fromDate) {
        return fromDate.isAfter(scheduleStartDate);
    }
}
