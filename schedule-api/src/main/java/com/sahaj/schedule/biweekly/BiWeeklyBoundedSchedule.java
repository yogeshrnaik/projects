package com.sahaj.schedule.biweekly;

import static java.time.temporal.TemporalAdjusters.previous;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.sahaj.schedule.BoundedSchedule;

public class BiWeeklyBoundedSchedule extends AbstractBiWeeklySchedule implements BoundedSchedule {

    private final LocalDate scheduleEndDate;

    public BiWeeklyBoundedSchedule(String eventName, LocalDate startDate, LocalTime scheduleTime, Set<DayOfWeek> daysOfWeek,
        LocalDate endDate) {
        super(eventName, startDate, scheduleTime, daysOfWeek);
        this.scheduleEndDate = getLastOccurrence(endDate.atTime(scheduleTime));
    }

    @Override
    protected Optional<LocalDateTime> getFirstOccurrenceFrom(LocalDateTime fromDate) {
        Optional<LocalDateTime> firstOccurFromDate = super.getFirstOccurrenceFrom(fromDate);
        return firstOccurFromDate.isPresent()
            ? checkIfBeforeEndDate(firstOccurFromDate.get().toLocalDate())
            : Optional.empty();
    }

    private Optional<LocalDateTime> checkIfBeforeEndDate(LocalDate occurrence) {
        return occurrence.isBefore(scheduleEndDate) ? Optional.of(occurrence.atTime(scheduleTime)) : Optional.empty();
    }

    private LocalDate getLastOccurrence(LocalDateTime endDate) {
        if (isDayOfWeekOfDateSameAsScheduleDay(endDate.toLocalDate())) {
            if (isWeeksBetweenEven(firstOccurenceOnOrAfterStartDate, endDate.toLocalDate())) {
                return endDate.toLocalDate();
            }
        }
        DayOfWeek prevDayOfWeek = getPreviousDayOfWeek(endDate.getDayOfWeek());
        return getLastOccurrence(endDate.with(previous(prevDayOfWeek)));
    }

    @Override
    protected Optional<LocalDateTime> getNextOccurrenceAfter(LocalDateTime currOccurrence) {
        Optional<LocalDateTime> nextOccurence = super.getNextOccurrenceAfter(currOccurrence);
        if (nextOccurence.isPresent()) {
            return nextOccurence.get().isBefore(endDate()) || nextOccurence.get().equals(endDate())
                ? nextOccurence
                : Optional.empty();
        }
        return Optional.empty();
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
}
