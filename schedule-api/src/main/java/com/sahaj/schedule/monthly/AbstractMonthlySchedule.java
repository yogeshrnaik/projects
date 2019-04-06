package com.sahaj.schedule.monthly;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import com.sahaj.schedule.AbstractSchedule;

public abstract class AbstractMonthlySchedule extends AbstractSchedule {

    protected final int dayOfMonth;

    public AbstractMonthlySchedule(String eventName, LocalDate startDate, LocalTime scheduleTime, int dayOfMonth) {
        super(eventName, startDate, scheduleTime);
        this.dayOfMonth = dayOfMonth;
    }

    @Override
    protected Optional<LocalDateTime> getFirstOccurrenceFrom(LocalDateTime fromDate) {
        if (fromDate.isBefore(scheduleStartDateTime)) {
            return getFirstOccurrenceFrom(scheduleStartDateTime);
        }

        if (fromDate.getDayOfMonth() == dayOfMonth) {
            if (fromDate.toLocalTime().isBefore(scheduleTime)
                || fromDate.toLocalTime().equals(scheduleTime)) {
                return Optional.of(fromDate.toLocalDate().atTime(scheduleTime));
            }
        } else if (fromDate.getDayOfMonth() < dayOfMonth) {
            final int validDayOfMonth = Math.min(dayOfMonth, fromDate.toLocalDate().lengthOfMonth());
            return Optional.of(fromDate.toLocalDate().withDayOfMonth(validDayOfMonth).atTime(scheduleTime));
        }

        return getOccurrence(fromDate);
    }

    private Optional<LocalDateTime> getOccurrence(LocalDateTime fromDate) {
        LocalDate next = fromDate.toLocalDate().plusMonths(1);
        final int validDayOfMonth = Math.min(dayOfMonth, next.lengthOfMonth());
        return Optional.of(next.withDayOfMonth(validDayOfMonth).atTime(scheduleTime));
    }

    protected boolean isDayOfMonthSameAsScheduleDay(LocalDate fromDate) {
        return dayOfMonth == fromDate.getDayOfMonth();
    }

}
