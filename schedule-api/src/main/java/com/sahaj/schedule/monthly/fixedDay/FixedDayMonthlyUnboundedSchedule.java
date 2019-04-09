package com.sahaj.schedule.monthly.fixedDay;

import static java.time.temporal.TemporalAdjusters.firstDayOfNextMonth;

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
        return Optional.of(getOccurrenceByOrdinal(currOccurrence.toLocalDate().with(firstDayOfNextMonth())));
    }
}
