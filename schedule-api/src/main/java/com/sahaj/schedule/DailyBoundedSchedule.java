package com.sahaj.schedule;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class DailyBoundedSchedule extends AbstractDailySchedule implements BoundedSchedule {

    private final LocalDateTime scheduleEndDate;

    public DailyBoundedSchedule(String eventName, LocalDateTime startDate, LocalDateTime endDate) {
        super(eventName, startDate);
        this.scheduleEndDate = endDate;
    }

    @Override
    public LocalDateTime startDate() {
        return super.startDate();
    }

    @Override
    public LocalDateTime endDate() {
        return scheduleEndDate;
    }

    @Override
    public List<LocalDateTime> getOccurrencesFrom(LocalDateTime startDate, int numberOfOccurences) {
        if (startDate.isAfter(scheduleEndDate)) {
            return Collections.emptyList();
        }
        return getLimitedOccurrencesFrom(startDate, numberOfOccurences)
            .stream()
            .filter(currDate -> !currDate.isAfter(scheduleEndDate))
            .collect(Collectors.toList());
    }

    @Override
    public List<LocalDateTime> getAllOccurrences() {
        long numOfDaysBetween = ChronoUnit.DAYS.between(scheduleStartDate, scheduleEndDate.plusDays(1));

        return LongStream.range(0, numOfDaysBetween)
            .mapToObj(i -> scheduleStartDate.plusDays(i))
            .collect(Collectors.toList());
    }

    @Override
    public int getNumberOfOccurences() {
        return getAllOccurrences().size();
    }
}
