package com.sahaj.schedule.monthly.fixedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import com.sahaj.schedule.AbstractSchedule;

public abstract class AbstractFixedDateMonthlySchedule extends AbstractSchedule {

    protected final int fixedDateOfMonth;

    public AbstractFixedDateMonthlySchedule(String eventName, LocalDate startDate, LocalTime scheduleTime, int dateOfMonth) {
        super(eventName, startDate, scheduleTime);
        this.fixedDateOfMonth = dateOfMonth;
    }

    @Override
    protected Optional<LocalDateTime> getFirstOccurrenceFrom(LocalDateTime fromDate) {
        if (fromDate.isBefore(scheduleStartDateTime)) {
            return getFirstOccurrenceFrom(scheduleStartDateTime);
        }
        if (isDateOfMonthBeforeFixedDate(fromDate.toLocalDate())) {
            final int validDayOfMonth = Math.min(fixedDateOfMonth, fromDate.toLocalDate().lengthOfMonth());
            return Optional.of(fromDate.toLocalDate().withDayOfMonth(validDayOfMonth).atTime(scheduleTime));
        } else if (isDateOfMonthSameAsFixedDay(fromDate.toLocalDate())) {
            if (fromDate.toLocalTime().isBefore(scheduleTime)
                || fromDate.toLocalTime().equals(scheduleTime)) {
                return Optional.of(fromDate.toLocalDate().atTime(scheduleTime));
            }
        }
        return getNextOccurrence(fromDate);
    }

    private Optional<LocalDateTime> getNextOccurrence(LocalDateTime fromDate) {
        LocalDate next = fromDate.toLocalDate().plusMonths(1);
        final int validDayOfMonth = Math.min(fixedDateOfMonth, next.lengthOfMonth());
        return Optional.of(next.withDayOfMonth(validDayOfMonth).atTime(scheduleTime));
    }

    protected boolean isDateOfMonthSameAsFixedDay(LocalDate fromDate) {
        return fixedDateOfMonth == fromDate.getDayOfMonth();
    }

    protected boolean isDateOfMonthBeforeFixedDate(LocalDate fromDate) {
        return fromDate.getDayOfMonth() < fixedDateOfMonth;
    }

}
