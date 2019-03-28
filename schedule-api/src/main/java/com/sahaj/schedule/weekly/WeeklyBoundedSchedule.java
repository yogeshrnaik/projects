package com.sahaj.schedule.weekly;

import static java.time.temporal.TemporalAdjusters.next;
import static java.time.temporal.TemporalAdjusters.previous;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
        return endDate.with(previous(prevDayOfWeek));
    }

    @Override
    protected Optional<LocalDateTime> getNextOccurrenceAfter(LocalDateTime currDate) {
        DayOfWeek nextDayOfWeek = getNextDayOfWeek(currDate.getDayOfWeek());

        LocalDateTime nextOccurence = currDate.toLocalDate().with(next(nextDayOfWeek)).atTime(scheduleTime);

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
        Long numOfDaysBetween = ChronoUnit.DAYS.between(scheduleStartDate, scheduleEndDate.plusDays(1));
        return getOccurrencesFrom(scheduleStartDateTime, numOfDaysBetween.intValue());
    }

    @Override
    public int getNumberOfOccurences() {
        return getAllOccurrences().size();
    }
}
