package com.sahaj.schedule.quaterly.fixedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Optional;

import com.sahaj.schedule.AbstractSchedule;

public abstract class AbstractFixedDateQuaterlySchedule extends AbstractSchedule {

    protected final int fixedDateOfMonth;

    public AbstractFixedDateQuaterlySchedule(String eventName, LocalDate startDate, LocalTime scheduleTime, int dateOfMonth) {
        super(eventName, startDate, scheduleTime);
        this.fixedDateOfMonth = dateOfMonth;
    }

    @Override
    protected Optional<LocalDateTime> getFirstOccurrenceFrom(LocalDateTime fromDate) {
        if (isStartingMonthOfQuarter(fromDate.toLocalDate())) {
            if (isDateOfMonthSameAsFixedDay(fromDate.toLocalDate())
                && (fromDate.toLocalTime().isBefore(scheduleTime) || fromDate.toLocalTime().equals(scheduleTime))) {
                return Optional.of(fromDate.toLocalDate().atTime(scheduleTime));
            } else {
                LocalDateTime dateWithFixedDateOfMonth = fromDate.toLocalDate().atTime(scheduleTime)
                    .withDayOfMonth(Math.min(fixedDateOfMonth, fromDate.toLocalDate().lengthOfMonth()));
                if (dateWithFixedDateOfMonth.isAfter(fromDate) || dateWithFixedDateOfMonth.equals(fromDate)) {
                    return Optional.of(dateWithFixedDateOfMonth);
                }
            }
        }
        return getFirstOccurrenceFrom(fromDate.toLocalDate().atTime(scheduleTime).plusMonths(1));
    }

    protected boolean isStartingMonthOfQuarter(LocalDate fromDate) {
        return fromDate.getMonth() == Month.JANUARY || fromDate.getMonth() == Month.APRIL
            || fromDate.getMonth() == Month.JULY || fromDate.getMonth() == Month.OCTOBER;
    }

    protected boolean isDateOfMonthSameAsFixedDay(LocalDate fromDate) {
        return fixedDateOfMonth == fromDate.getDayOfMonth();
    }

    protected boolean isDateOfMonthBeforeFixedDate(LocalDate fromDate) {
        return fromDate.getDayOfMonth() < fixedDateOfMonth;
    }

}
