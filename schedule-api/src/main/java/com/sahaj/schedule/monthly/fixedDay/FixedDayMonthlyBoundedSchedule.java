package com.sahaj.schedule.monthly.fixedDay;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.firstDayOfNextMonth;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import com.sahaj.schedule.BoundedSchedule;

public class FixedDayMonthlyBoundedSchedule extends AbstractFixedDayMonthlySchedule implements BoundedSchedule {

    private final LocalDate scheduleEndDate;

    public FixedDayMonthlyBoundedSchedule(String eventName, LocalDate startDate, LocalTime scheduleTime,
        Ordinal ordinal, DayOfWeek fixedDayOfWeek, LocalDate endDate) {
        super(eventName, startDate, scheduleTime, ordinal, fixedDayOfWeek);
        this.scheduleEndDate = getLastOccurrence(endDate);
    }

    private LocalDate getLastOccurrence(LocalDate endDate) {

        LocalDateTime fixedDayInMonthOfEndDate = getOccurrenceByOrdinal(endDate);

        if (fixedDayInMonthOfEndDate.toLocalDate().isBefore(endDate)
            || fixedDayInMonthOfEndDate.toLocalDate().isEqual(endDate)) {
            return fixedDayInMonthOfEndDate.toLocalDate();
        }

        LocalDate lastDayOfPrevMonth = endDate.with(firstDayOfMonth()).minusDays(1);
        return getOccurrenceByOrdinal(lastDayOfPrevMonth).toLocalDate();
    }

    @Override
    protected Optional<LocalDateTime> getNextOccurrenceAfter(LocalDateTime currOccurrence) {
        LocalDateTime nextOccurence = getOccurrenceByOrdinal(currOccurrence.toLocalDate().with(firstDayOfNextMonth()));
        return nextOccurence.isBefore(endDate()) || nextOccurence.equals(endDate())
            ? Optional.of(nextOccurence)
            : Optional.empty();
    }

    @Override
    public LocalDateTime endDate() {
        return scheduleEndDate.atTime(scheduleTime);
    }

    @Override
    public List<LocalDateTime> getAllOccurrences() {
        Long numOfMonthsBetween = 1 + ChronoUnit.MONTHS.between(scheduleStartDate, scheduleEndDate);
        return getOccurrencesFrom(scheduleStartDateTime, numOfMonthsBetween.intValue());
    }

    @Override
    public int getNumberOfOccurences() {
        return getAllOccurrences().size();
    }
}
