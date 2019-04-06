package com.sahaj.schedule.monthly.fixedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import com.sahaj.schedule.BoundedSchedule;

public class FixedDateMonthlyBoundedSchedule extends AbstractFixedDateMonthlySchedule implements BoundedSchedule {

    private final LocalDate scheduleEndDate;

    public FixedDateMonthlyBoundedSchedule(String eventName, LocalDate startDate, LocalTime scheduleTime, int dateOfMonth,
        LocalDate endDate) {
        super(eventName, startDate, scheduleTime, dateOfMonth);
        this.scheduleEndDate = getLastOccurrence(endDate);
    }

    private LocalDate getLastOccurrence(LocalDate endDate) {
        if (isDateOfMonthSameAsFixedDay(endDate)) {
            return endDate;
        }

        LocalDate prev = endDate.minusMonths(1);
        return prev.withDayOfMonth(Math.min(fixedDateOfMonth, prev.lengthOfMonth()));
    }

    @Override
    protected Optional<LocalDateTime> getNextOccurrenceAfter(LocalDateTime currOccurrence) {
        LocalDate next = currOccurrence.toLocalDate().plusMonths(1);
        int validDayOfMonth = Math.min(fixedDateOfMonth, next.lengthOfMonth());
        LocalDateTime nextOccurence = next.withDayOfMonth(validDayOfMonth).atTime(scheduleTime);
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
