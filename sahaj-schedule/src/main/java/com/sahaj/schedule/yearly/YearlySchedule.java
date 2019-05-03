package com.sahaj.schedule.yearly;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.sahaj.schedule.AbstractSchedule;

public class YearlySchedule extends AbstractSchedule {

    private LocalDate startDate;
    private LocalTime scheduleTime;

    public YearlySchedule(String eventName, LocalDate startDate, LocalTime scheduleTime) {
        super(eventName);
        this.startDate = startDate;
        this.scheduleTime = scheduleTime;
    }

    public LocalDateTime startDate() {
        return startDate.atTime(scheduleTime);
    }

    public List<LocalDateTime> getOccurrences(int limitNumberOfOccurences) {
        return null;
    }

    public List<LocalDateTime> getOccurrencesFrom(LocalDateTime fromDate, int numberOfOccurences) {
        return null;
    }

    @Override
    protected LocalDate getFirstOccurrenceFrom(LocalDate fromDate) {
        return (fromDate.isAfter(startDate)) ? startDate.plusYears(1) : startDate;
    }
}
