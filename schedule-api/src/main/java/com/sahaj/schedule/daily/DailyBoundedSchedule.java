package com.sahaj.schedule.daily;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import com.sahaj.schedule.BoundedSchedule;

public class DailyBoundedSchedule extends AbstractDailySchedule implements BoundedSchedule {

    private final LocalDate scheduleEndDate;

    public DailyBoundedSchedule(String eventName, LocalDate startDate, LocalDate endDate, LocalTime time) {
        super(eventName, startDate, time);
        this.scheduleEndDate = endDate;
    }

    @Override
    public LocalDateTime startDate() {
        return super.startDate();
    }

    @Override
    public LocalDateTime endDate() {
        return scheduleEndDate.atTime(scheduleTime);
    }

    @Override
    public List<LocalDateTime> getAllOccurrences() {
        Long numOfDaysBetween = ChronoUnit.DAYS.between(scheduleStartDate, scheduleEndDate.plusDays(1));
        return getOccurrencesFrom(scheduleStartDateTime, numOfDaysBetween.intValue());
    }

    @Override
    public int getNumberOfOccurences() {
        return getAllOccurrences().size();
    }

    protected Optional<LocalDateTime> getFirstOccurrenceFrom(LocalDateTime fromDate) {
        Optional<LocalDateTime> firstOccurrenceFrom = super.getFirstOccurrenceFrom(fromDate);
        return (firstOccurrenceFrom.isPresent() && isBeforeOrEqualToEndDate(firstOccurrenceFrom.get()))
            ? firstOccurrenceFrom
            : Optional.empty();
    }

    private boolean isBeforeOrEqualToEndDate(LocalDateTime occurrence) {
        return occurrence.isBefore(endDate()) || occurrence.equals(endDate());
    }

    @Override
    protected Optional<LocalDateTime> getNextOccurrenceAfter(LocalDateTime currDate) {
        LocalDateTime nextOccurence = currDate.plusDays(1);
        return nextOccurence.isBefore(endDate()) || nextOccurence.equals(endDate())
            ? Optional.of(nextOccurence)
            : Optional.empty();
    }
}
