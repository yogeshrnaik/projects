package com.sahaj.schedule.biweekly;

import static java.time.temporal.TemporalAdjusters.next;
import static java.time.temporal.TemporalAdjusters.previous;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;

import com.sahaj.schedule.weekly.AbstractWeeklySchedule;

public class BiWeeklyUnboundedSchedule extends AbstractWeeklySchedule {

    public BiWeeklyUnboundedSchedule(String eventName, LocalDate startDate, LocalTime scheduleTime, Set<DayOfWeek> daysOfWeek) {
        super(eventName, startDate, scheduleTime, daysOfWeek);
    }

    @Override
    protected Optional<LocalDateTime> getFirstOccurrenceFrom(LocalDateTime fromDate) {
        // TODO: change implementation
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

    @Override
    protected Optional<LocalDateTime> getNextOccurrenceAfter(LocalDateTime currOccurrence) {
        if (daysOfWeek.size() == 1) {
            // add two weeks to currDate to calculate next occurrence
            return Optional.of(currOccurrence.plusWeeks(2));
        }

        // if currDate's day is not last in the set of daysOfWeek
        if (!isLastDayInDaysOfWeek(currOccurrence.getDayOfWeek())) {
            // find the next day and return date as of that day
            DayOfWeek nextDayOfWeek = getNextDayOfWeekAfter(currOccurrence.getDayOfWeek());
            return Optional.of(currOccurrence.toLocalDate().with(next(nextDayOfWeek)).atTime(scheduleTime));
        }

        // if currDate's day is last in the set of daysOfWeek
        // then find the date as of the 1st day of week from the set of daysOfWeek
        // and add two weeks to it
        return Optional.of(currOccurrence.toLocalDate()
            .with(previous(daysOfWeek.get(0)))
            .plusWeeks(2).atTime(scheduleTime));
    }

    private boolean isLastDayInDaysOfWeek(DayOfWeek dayOfWeekToFind) {
        return daysOfWeek.get(daysOfWeek.size() - 1).equals(dayOfWeekToFind);
    }

    private DayOfWeek getNextDayOfWeekAfter(DayOfWeek dayOfWeekToFind) {
        int index = daysOfWeek.indexOf(dayOfWeekToFind);
        return daysOfWeek.get(index + 1);
    }

}
