package com.sahaj.schedule.monthly;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class MonthlyUnboundedSchedule extends AbstractMonthlySchedule {

    public MonthlyUnboundedSchedule(String eventName, LocalDate startDate, LocalTime scheduleTime, int dayOfMonth) {
        super(eventName, startDate, scheduleTime, dayOfMonth);
    }

    @Override
    protected Optional<LocalDateTime> getNextOccurrenceAfter(LocalDateTime currOccurrence) {
        LocalDate next = currOccurrence.toLocalDate().plusMonths(1);
        int validDayOfMonth = Math.min(dayOfMonth, next.lengthOfMonth());
        return Optional.of(next.withDayOfMonth(validDayOfMonth).atTime(scheduleTime));
    }
}
