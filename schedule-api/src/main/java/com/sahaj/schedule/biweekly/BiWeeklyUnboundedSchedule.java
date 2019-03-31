package com.sahaj.schedule.biweekly;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class BiWeeklyUnboundedSchedule extends AbstractBiWeeklySchedule {

    public BiWeeklyUnboundedSchedule(String eventName, LocalDate startDate, LocalTime scheduleTime, Set<DayOfWeek> daysOfWeek) {
        super(eventName, startDate, scheduleTime, daysOfWeek);
    }
}
