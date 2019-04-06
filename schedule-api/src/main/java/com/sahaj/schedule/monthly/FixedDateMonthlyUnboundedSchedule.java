package com.sahaj.schedule.monthly;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class FixedDateMonthlyUnboundedSchedule extends AbstractFixedDateMonthlySchedule {

    public FixedDateMonthlyUnboundedSchedule(String eventName, LocalDate startDate, LocalTime scheduleTime, int dateOfMonth) {
        super(eventName, startDate, scheduleTime, dateOfMonth);
    }

    @Override
    protected Optional<LocalDateTime> getNextOccurrenceAfter(LocalDateTime currOccurrence) {
        LocalDate next = currOccurrence.toLocalDate().plusMonths(1);
        int validDateOfMonth = Math.min(fixedDateOfMonth, next.lengthOfMonth());
        return Optional.of(next.withDayOfMonth(validDateOfMonth).atTime(scheduleTime));
    }
}
