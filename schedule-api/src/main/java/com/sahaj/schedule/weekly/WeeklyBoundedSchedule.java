package com.sahaj.schedule.weekly;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.sahaj.schedule.BoundedSchedule;

public class WeeklyBoundedSchedule extends AbstractWeeklySchedule implements BoundedSchedule {

    private final LocalDate scheduleEndDate;

    public WeeklyBoundedSchedule(String eventName, LocalDate startDate, LocalTime scheduleTime, Set<DayOfWeek> daysOfWeek,
        LocalDate endDate) {
        super(eventName, startDate, scheduleTime, daysOfWeek);
        this.scheduleEndDate = getLastOccurrence(endDate);
    }

    private LocalDate getLastOccurrence(LocalDate endDate) {
        if (isDayOfWeekOfDateSameAsScheduleDay(endDate)) {
            return endDate;
        }

        DayOfWeek prevDayOfWeek = getPreviousDayOfWeek(endDate.getDayOfWeek());
        return endDate.with(TemporalAdjusters.previous(prevDayOfWeek));
    }

    @Override
    protected Optional<LocalDateTime> getNextOccurrenceAfter(LocalDateTime currDate) {
        DayOfWeek nextDayOfWeek = getNextDayOfWeek(currDate.getDayOfWeek());
        return Optional.of(currDate.toLocalDate()
            .with(TemporalAdjusters.next(nextDayOfWeek))
            .atTime(scheduleTime));
    }

    @Override
    public LocalDateTime endDate() {
        return scheduleEndDate.atTime(scheduleTime);
    }

    @Override
    public List<LocalDateTime> getAllOccurrences() {
        return null;
    }

    @Override
    public int getNumberOfOccurences() {
        return 0;
    }
}
