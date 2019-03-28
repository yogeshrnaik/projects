package com.sahaj.schedule.weekly;

import static java.time.temporal.TemporalAdjusters.next;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;

public class WeeklyUnboundedSchedule extends AbstractWeeklySchedule {

    public WeeklyUnboundedSchedule(String eventName, LocalDate startDate, LocalTime scheduleTime, Set<DayOfWeek> daysOfWeek) {
        super(eventName, startDate, scheduleTime, daysOfWeek);
    }

    @Override
    protected Optional<LocalDateTime> getNextOccurrenceAfter(LocalDateTime currOccurrence) {
        DayOfWeek nextDayOfWeek = getNextDayOfWeek(currOccurrence.getDayOfWeek());
        return Optional.of(currOccurrence.toLocalDate()
            .with(next(nextDayOfWeek))
            .atTime(scheduleTime));
    }
}
