package com.sahaj.schedule.biweekly;

import static java.time.temporal.TemporalAdjusters.next;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import com.sahaj.schedule.weekly.AbstractWeeklySchedule;

public class AbstractBiWeeklySchedule extends AbstractWeeklySchedule {

    protected final LocalDate firstOccurenceOnOrBeforeStartDate;
    protected final LocalDate firstOccurenceOnOrAfterStartDate;
    protected final int weekOfFirstOccurrence;

    public AbstractBiWeeklySchedule(String eventName, LocalDate startDate, LocalTime scheduleTime, Set<DayOfWeek> daysOfWeek) {
        super(eventName, startDate, scheduleTime, daysOfWeek);
        this.firstOccurenceOnOrBeforeStartDate = getFirstOccurrenceOnOrBeforeStartDate();
        this.firstOccurenceOnOrAfterStartDate = getFirstOccurrenceOnOrAfterStartDate();
        this.weekOfFirstOccurrence = firstOccurenceOnOrAfterStartDate.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
    }

    private LocalDate getFirstOccurrenceOnOrBeforeStartDate() {
        if (isDayOfWeekOfDateSameAsScheduleDay(scheduleStartDateTime.toLocalDate())) {
            return scheduleStartDate;
        } else {
            return scheduleStartDate.with(TemporalAdjusters.previous(daysOfWeek.get(0)));
        }
    }

    private LocalDate getFirstOccurrenceOnOrAfterStartDate() {
        if (isDayOfWeekOfDateSameAsScheduleDay(scheduleStartDateTime.toLocalDate())) {
            return scheduleStartDate;
        } else {
            DayOfWeek nextDayOfWeekOfSchedule = getNextDayOfWeek(scheduleStartDate.getDayOfWeek());
            return scheduleStartDate.with(next(nextDayOfWeekOfSchedule));
        }
    }

    @Override
    protected Optional<LocalDateTime> getFirstOccurrenceFrom(LocalDateTime fromDate) {
        if (fromDate.isBefore(scheduleStartDateTime)) {
            return getFirstOccurrenceFrom(scheduleStartDateTime);
        }

        if (isDayOfWeekOfDateSameAsScheduleDay(fromDate.toLocalDate())) {
            if (isWeeksBetweenEven(fromDate.toLocalDate())) {
                if (fromDate.toLocalTime().isBefore(scheduleTime)
                    || fromDate.toLocalTime().equals(scheduleTime)) {
                    return Optional.of(fromDate.toLocalDate().atTime(scheduleTime));
                }
            }
        }
        DayOfWeek nextDayOfWeek = getNextDayOfWeek(fromDate.getDayOfWeek());
        return getFirstOccurrenceFrom(fromDate.toLocalDate().atTime(scheduleTime).with(next(nextDayOfWeek)));
    }

    protected boolean isWeeksBetweenEven(LocalDate toDate) {
        int weekOfToDate = toDate.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
        return (weekOfToDate % 2 == 0 && weekOfFirstOccurrence % 2 == 0)
            || (weekOfToDate % 2 != 0 && weekOfFirstOccurrence % 2 != 0);
    }

    protected boolean isWeeksBetweenEven(LocalDate fromDate, LocalDate toDate) {
        long weeksBetween = ChronoUnit.WEEKS.between(fromDate, toDate);
        return (weeksBetween % 2 == 0);
    }

    @Override
    protected Optional<LocalDateTime> getNextOccurrenceAfter(LocalDateTime currOccurrence) {
        DayOfWeek nextDayOfWeek = getNextDayOfWeek(currOccurrence.getDayOfWeek());

        LocalDateTime nextOccurence = currOccurrence.toLocalDate().with(next(nextDayOfWeek)).atTime(scheduleTime);
        if (isWeeksBetweenEven(nextOccurence.toLocalDate())) {
            return Optional.of(nextOccurence);
        } else {
            return getNextOccurrenceAfter(nextOccurence);
        }
    }
}
