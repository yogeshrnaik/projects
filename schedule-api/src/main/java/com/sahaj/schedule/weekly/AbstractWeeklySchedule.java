package com.sahaj.schedule.weekly;

import static java.time.temporal.TemporalAdjusters.next;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import com.sahaj.schedule.AbstractSchedule;

public abstract class AbstractWeeklySchedule extends AbstractSchedule {

    protected final Set<DayOfWeek> daysOfWeek;

    public AbstractWeeklySchedule(String eventName, LocalDate startDate, LocalTime scheduleTime, Set<DayOfWeek> daysOfWeek) {
        super(eventName, startDate, scheduleTime);
        this.daysOfWeek = new TreeSet<>(daysOfWeek); // need to keep days of week sorted
    }

    @Override
    protected Optional<LocalDateTime> getFirstOccurrenceFrom(LocalDateTime fromDate) {
        if (isDayOfWeekOfDateSameAsScheduleDay(fromDate.toLocalDate())) {
            if (fromDate.isAfter(scheduleStartDateTime) || fromDate.equals(scheduleStartDateTime)) {
                if (fromDate.toLocalTime().isBefore(scheduleTime)
                    || fromDate.toLocalTime().equals(scheduleTime)) {
                    // fromDate itself is 1st occurrence
                    return Optional.of(fromDate.toLocalDate().atTime(scheduleTime));
                }
            }
        }
        DayOfWeek nextDayOfWeek = getNextDayOfWeek(fromDate.getDayOfWeek());
        return Optional.of(fromDate.toLocalDate().with(next(nextDayOfWeek)).atTime(scheduleTime));
    }

    protected boolean isDayOfWeekOfDateSameAsScheduleDay(LocalDate fromDate) {
        return daysOfWeek.contains(fromDate.getDayOfWeek());
    }

    protected DayOfWeek getNextDayOfWeek(DayOfWeek currDayOfWeek) {
        DayOfWeek next = currDayOfWeek;
        do {
            next = next.plus(1);
        } while (!daysOfWeek.contains(next));
        return next;
    }

    protected DayOfWeek getPreviousDayOfWeek(DayOfWeek currDayOfWeek) {
        DayOfWeek next = currDayOfWeek;
        do {
            next = next.minus(1);
        } while (!daysOfWeek.contains(next));
        return next;
    }

}
