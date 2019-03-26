package com.sahaj.schedule.daily;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.sahaj.schedule.BoundedSchedule;

public class DailyBoundedSchedule extends AbstractDailySchedule implements BoundedSchedule {

    private final LocalDateTime scheduleEndDate;

    public DailyBoundedSchedule(String eventName, LocalDateTime startDate, LocalDateTime endDate) {
        super(eventName, startDate);
        this.scheduleEndDate = endDate;
    }

    @Override
    public LocalDateTime startDate() {
        return super.startDate();
    }

    @Override
    public LocalDateTime endDate() {
        return scheduleEndDate;
    }

    @Override
    public List<LocalDateTime> getAllOccurrences() {
        Long numOfDaysBetween = ChronoUnit.DAYS.between(scheduleStartDate, scheduleEndDate.plusDays(1));
        return getOccurrencesFrom(scheduleStartDate, numOfDaysBetween.intValue());
    }

    @Override
    public int getNumberOfOccurences() {
        return getAllOccurrences().size();
    }

    protected LocalDateTime getFirstOccurrenceFrom(LocalDateTime fromDate) {
        LocalDateTime firstOccurrenceFrom = super.getFirstOccurrenceFrom(fromDate);
        return (firstOccurrenceFrom != null &&
            (firstOccurrenceFrom.isBefore(scheduleEndDate) || firstOccurrenceFrom.equals(scheduleEndDate)))
                ? firstOccurrenceFrom
                : null;
    }

    @Override
    protected LocalDateTime getNextOccurrenceAfter(LocalDateTime currDate) {
        LocalDateTime nextOccurence = currDate.plusDays(1);
        return nextOccurence.isBefore(scheduleEndDate) || nextOccurence.equals(scheduleEndDate)
            ? nextOccurence
            : null;
    }
}
