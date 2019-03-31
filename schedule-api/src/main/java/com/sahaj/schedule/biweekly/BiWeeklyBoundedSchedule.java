package com.sahaj.schedule.biweekly;

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
import com.sahaj.schedule.weekly.AbstractWeeklySchedule;

public class BiWeeklyBoundedSchedule extends AbstractWeeklySchedule implements BoundedSchedule {

    private final LocalDate scheduleEndDate;

    public BiWeeklyBoundedSchedule(String eventName, LocalDate startDate, LocalTime scheduleTime, Set<DayOfWeek> daysOfWeek,
        LocalDate endDate) {
        super(eventName, startDate, scheduleTime, daysOfWeek);
        this.scheduleEndDate = getLastOccurrence(endDate.atTime(scheduleTime));
    }

    @Override
    protected Optional<LocalDateTime> getFirstOccurrenceFrom(LocalDateTime fromDate) {
        LocalDateTime firstOccurFromStart = getFirstOccurrenceFromScheduleStartDate();
        Optional<LocalDateTime> firstOccurFromDate = super.getFirstOccurrenceFrom(fromDate);
        if (firstOccurFromDate.isPresent()) {
            long weeksBetween = ChronoUnit.WEEKS.between(firstOccurFromStart, firstOccurFromDate.get());
            if (weeksBetween % 2 == 0) {
                return checkIfBeforeEndDate(firstOccurFromDate.get().toLocalDate());
            } else {
                getFirstOccurrenceFrom(firstOccurFromDate.get());
            }
        }
        return Optional.empty();
    }

    private Optional<LocalDateTime> checkIfBeforeEndDate(LocalDate occurrence) {
        return occurrence.isBefore(scheduleEndDate) ? Optional.of(occurrence.atTime(scheduleTime)) : Optional.empty();
    }

    private LocalDateTime getFirstOccurrenceFromScheduleStartDate() {
        if (isDayOfWeekOfDateSameAsScheduleDay(scheduleStartDateTime.toLocalDate())) {
            return scheduleStartDateTime;
        } else {
            DayOfWeek nextDayOfWeekOfSchedule = getNextDayOfWeek(scheduleStartDateTime.getDayOfWeek());
            LocalDateTime firstOccurrence = scheduleStartDateTime.with(next(nextDayOfWeekOfSchedule));
            return firstOccurrence;
        }
    }

    private LocalDate getLastOccurrence(LocalDateTime endDate) {
        LocalDateTime firstOccurFromStart = getFirstOccurrenceFromScheduleStartDate();
        if (isDayOfWeekOfDateSameAsScheduleDay(endDate.toLocalDate())) {
            // check the difference of weeks between firstOccurrence and endDate
            long weeksBetween = ChronoUnit.WEEKS.between(firstOccurFromStart, endDate);
            if (weeksBetween % 2 == 0) {
                return endDate.toLocalDate();
            } else {
                DayOfWeek prevDayOfWeek = getPreviousDayOfWeek(endDate.getDayOfWeek());
                return getLastOccurrence(endDate.with(previous(prevDayOfWeek)));
            }
        } else {
            DayOfWeek prevDayOfWeek = getPreviousDayOfWeek(endDate.getDayOfWeek());
            return getLastOccurrence(endDate.with(previous(prevDayOfWeek)));
        }
    }

    @Override
    protected Optional<LocalDateTime> getNextOccurrenceAfter(LocalDateTime currOccurrence) {
        // TODO: change implementation
        DayOfWeek nextDayOfWeek = getNextDayOfWeek(currOccurrence.getDayOfWeek());

        LocalDateTime nextOccurence = currOccurrence.toLocalDate().with(next(nextDayOfWeek)).atTime(scheduleTime);

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
        // TODO: change implementation
        Long numOfDaysBetween = ChronoUnit.DAYS.between(scheduleStartDate, scheduleEndDate.plusDays(1));
        return getOccurrencesFrom(scheduleStartDateTime, numOfDaysBetween.intValue());
    }

    @Override
    public int getNumberOfOccurences() {
        return getAllOccurrences().size();
    }
}
