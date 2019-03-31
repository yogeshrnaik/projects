package com.sahaj.schedule.biweekly;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

import com.sahaj.schedule.BoundedSchedule;
import com.sahaj.schedule.Schedule;
import com.sahaj.schedule.weekly.WeeklyScheduleBuilder;

public class BiWeeklyScheduleBuilder extends WeeklyScheduleBuilder {

    public BiWeeklyScheduleBuilder(String eventName, Set<DayOfWeek> daysOfWeek) {
        super(eventName, daysOfWeek);
    }

    @Override
    public BoundedSchedule endingOn(LocalDate end) {
        this.endDate = end;
        validate();
        return new BiWeeklyBoundedSchedule(eventName, startDate, time, daysOfWeek, endDate);
    }

    @Override
    public Schedule neverEnding() {
        validate();
        return new BiWeeklyUnboundedSchedule(eventName, startDate, time, daysOfWeek);
    }

}
