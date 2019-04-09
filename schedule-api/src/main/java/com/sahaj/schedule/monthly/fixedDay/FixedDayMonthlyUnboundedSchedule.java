package com.sahaj.schedule.monthly.fixedDay;

import static java.time.temporal.TemporalAdjusters.dayOfWeekInMonth;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class FixedDayMonthlyUnboundedSchedule extends AbstractFixedDayMonthlySchedule {

    public FixedDayMonthlyUnboundedSchedule(String eventName, LocalDate startDate, LocalTime scheduleTime,
        Ordinal ordinal, DayOfWeek fixedDayOfWeek) {
        super(eventName, startDate, scheduleTime, ordinal, fixedDayOfWeek);
    }

    @Override
    protected Optional<LocalDateTime> getNextOccurrenceAfter(LocalDateTime currOccurrence) {
        LocalDate next = currOccurrence.toLocalDate().plusMonths(1).with(dayOfWeekInMonth(ordinal.getOrdinal(), fixedDayOfWeek));
        return Optional.of(next.atTime(scheduleTime));
    }
}
