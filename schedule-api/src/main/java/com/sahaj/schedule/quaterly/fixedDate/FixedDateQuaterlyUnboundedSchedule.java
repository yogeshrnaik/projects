package com.sahaj.schedule.quaterly.fixedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class FixedDateQuaterlyUnboundedSchedule extends AbstractFixedDateQuaterlySchedule {

    public FixedDateQuaterlyUnboundedSchedule(String eventName, LocalDate startDate, LocalTime scheduleTime, int dateOfMonth) {
        super(eventName, startDate, scheduleTime, dateOfMonth);
    }

    @Override
    protected Optional<LocalDateTime> getNextOccurrenceAfter(LocalDateTime currOccurrence) {
        LocalDate next = currOccurrence.toLocalDate().plusMonths(3);
        int validDateOfMonth = Math.min(fixedDateOfMonth, next.lengthOfMonth());
        return Optional.of(next.withDayOfMonth(validDateOfMonth).atTime(scheduleTime));
    }
}
