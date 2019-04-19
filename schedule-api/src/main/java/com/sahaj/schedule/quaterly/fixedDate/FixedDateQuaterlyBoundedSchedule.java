package com.sahaj.schedule.quaterly.fixedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import com.sahaj.schedule.BoundedSchedule;

public class FixedDateQuaterlyBoundedSchedule extends AbstractFixedDateQuaterlySchedule implements BoundedSchedule {

    private final LocalDate scheduleEndDate;

    public FixedDateQuaterlyBoundedSchedule(String eventName, LocalDate startDate, LocalTime scheduleTime, int dateOfMonth,
        LocalDate endDate) {
        super(eventName, startDate, scheduleTime, dateOfMonth);
        this.scheduleEndDate = getLastOccurrence(endDate);
    }

    private LocalDate getLastOccurrence(LocalDate endDate) {
        if (isStartingMonthOfQuarter(endDate)) {
            if (isDateOfMonthSameAsFixedDay(endDate)) {
                return endDate;
            } else {
                return endDate.withDayOfMonth(Math.min(fixedDateOfMonth, endDate.lengthOfMonth()));
            }
        }
        return getLastOccurrence(endDate.minusMonths(1));
    }

    @Override
    protected Optional<LocalDateTime> getNextOccurrenceAfter(LocalDateTime currOccurrence) {
        LocalDate next = currOccurrence.toLocalDate().plusMonths(3);
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
        Long numOfMonthsBetween = 1 + (ChronoUnit.MONTHS.between(scheduleStartDate, scheduleEndDate) / 3);
        return getOccurrencesFrom(scheduleStartDateTime, numOfMonthsBetween.intValue());
    }

    @Override
    public int getNumberOfOccurences() {
        return getAllOccurrences().size();
    }
}
