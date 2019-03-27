package com.sahaj.schedule.weekly;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Optional;
import java.util.Set;

public class WeeklyUnboundedSchedule extends AbstractWeeklySchedule {

    public WeeklyUnboundedSchedule(String eventName, LocalDate startDate, LocalTime scheduleTime, Set<DayOfWeek> daysOfWeek) {
        super(eventName, startDate, scheduleTime, daysOfWeek);
    }

    @Override
    protected Optional<LocalDateTime> getNextOccurrenceAfter(LocalDateTime currDate) {
        DayOfWeek nextDayOfWeek = getNextDayOfWeek(currDate.getDayOfWeek());
        return Optional.of(currDate.toLocalDate()
            .with(TemporalAdjusters.next(nextDayOfWeek))
            .atTime(scheduleTime));
    }
}
