package com.sahaj.schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class AbstractSchedule implements Schedule {

    private final String eventName;
    protected final LocalDate scheduleStartDate;
    protected final LocalDateTime scheduleStartDateTime;
    protected final LocalTime scheduleTime;

    public AbstractSchedule(String eventName, LocalDate startDate, LocalTime scheduleTime) {
        this.eventName = eventName;
        this.scheduleStartDate = startDate;
        this.scheduleStartDateTime = scheduleStartDate.atTime(scheduleTime);
        this.scheduleTime = scheduleTime;
    }

    @Override
    public String getEventName() {
        return eventName;
    }

    @Override
    public LocalDateTime startDate() {
        return getFirstOccurrenceFrom(scheduleStartDateTime).get();
    }

    @Override
    public List<LocalDateTime> getOccurrences(int limitNumberOfOccurences) {
        return getOccurrencesFrom(scheduleStartDateTime, limitNumberOfOccurences);
    }

    @Override
    public List<LocalDateTime> getOccurrencesFrom(LocalDateTime fromDate, int numberOfOccurences) {
        Optional<LocalDateTime> firstOccurrence = getFirstOccurrenceFrom(fromDate);
        if (!firstOccurrence.isPresent() || numberOfOccurences <= 0) {
            return Collections.emptyList();
        }

        return getNextOccurrencesFrom(firstOccurrence.get(), numberOfOccurences);
    }

    private List<LocalDateTime> getNextOccurrencesFrom(LocalDateTime firstOccurrence, int numberOfOccurences) {
        List<LocalDateTime> occurrences = new ArrayList<>(Arrays.asList(firstOccurrence));
        Optional<LocalDateTime> currOccurrence = Optional.of(firstOccurrence);
        int counter = 1;
        while (counter < numberOfOccurences && currOccurrence.isPresent()) {
            currOccurrence = getNextOccurrenceAfter(currOccurrence.get());
            if (currOccurrence.isPresent()) {
                occurrences.add(currOccurrence.get());
                counter++;
            }
        }
        return occurrences;
    }

    protected abstract Optional<LocalDateTime> getFirstOccurrenceFrom(LocalDateTime fromDate);

    protected abstract Optional<LocalDateTime> getNextOccurrenceAfter(LocalDateTime currDate);

    protected boolean isFromDateBeforeScheduleStartDate(LocalDateTime fromDate) {
        return fromDate.isBefore(scheduleStartDateTime);
    }

    protected boolean isFromDateEqualsScheduleStartDate(LocalDateTime fromDate) {
        return fromDate.equals(scheduleStartDateTime);
    }

    protected boolean isFromDateAfterScheduleStartDate(LocalDateTime fromDate) {
        return fromDate.isAfter(scheduleStartDateTime);
    }
}
