package com.sahaj.schedule.monthly.fixedDay;

import static com.sahaj.schedule.monthly.fixedDay.Ordinal.FOURTH;
import static java.time.temporal.TemporalAdjusters.dayOfWeekInMonth;
import static java.time.temporal.TemporalAdjusters.firstDayOfNextMonth;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import com.sahaj.schedule.AbstractSchedule;

public abstract class AbstractFixedDayMonthlySchedule extends AbstractSchedule {

    protected final Ordinal ordinal;
    protected final DayOfWeek fixedDayOfWeek;

    public AbstractFixedDayMonthlySchedule(String eventName, LocalDate startDate, LocalTime scheduleTime,
        Ordinal ordinal, DayOfWeek fixedDayOfWeek) {
        super(eventName, startDate, scheduleTime);
        this.ordinal = ordinal;
        this.fixedDayOfWeek = fixedDayOfWeek;
    }

    @Override
    protected Optional<LocalDateTime> getFirstOccurrenceFrom(LocalDateTime fromDate) {
        if (fromDate.isBefore(scheduleStartDateTime)) {
            return getFirstOccurrenceFrom(scheduleStartDateTime);
        }

        LocalDateTime fixedDayInMonthOfFromDate = getOccurrence(fromDate);

        if (fixedDayInMonthOfFromDate.isAfter(fromDate) || fixedDayInMonthOfFromDate.isEqual(fromDate)) {
            return Optional.of(fixedDayInMonthOfFromDate);
        }

        LocalDateTime firstDayOfNextMonthAfterFromDate = fromDate.toLocalDate().atTime(scheduleTime).with(firstDayOfNextMonth());
        return Optional.of(getOccurrence(firstDayOfNextMonthAfterFromDate));
    }

    protected LocalDateTime getOccurrence(LocalDateTime fromDate) {
        LocalDateTime fixedDayInMonthOfFromDate = fromDate.with(dayOfWeekInMonth(ordinal.getOrdinal(), fixedDayOfWeek));
        if (fixedDayInMonthOfFromDate.getMonthValue() != fromDate.getMonthValue()) {
            fixedDayInMonthOfFromDate = fromDate.with(dayOfWeekInMonth(FOURTH.getOrdinal(), fixedDayOfWeek));
        }
        return fixedDayInMonthOfFromDate;
    }
}
