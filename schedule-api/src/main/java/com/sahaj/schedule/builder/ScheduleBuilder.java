package com.sahaj.schedule.builder;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import com.sahaj.schedule.biweekly.BiWeeklyScheduleBuilder;
import com.sahaj.schedule.daily.DailyScheduleBuilder;
import com.sahaj.schedule.monthly.fixedDate.FixedDateMonthlyScheduleBuilder;
import com.sahaj.schedule.monthly.fixedDay.FixedDayMonthlyScheduleBuilder;
import com.sahaj.schedule.monthly.fixedDay.Ordinal;
import com.sahaj.schedule.once.NonRecurringScheduleBuilder;
import com.sahaj.schedule.quaterly.fixedDate.FixedDateQuaterlyScheduleBuilder;
import com.sahaj.schedule.weekly.WeeklyScheduleBuilder;

public class ScheduleBuilder {

    private String eventName;

    protected ScheduleBuilder(String eventName) {
        this.eventName = eventName;
    }

    public static ScheduleBuilder newSchedule(String eventName) {
        return new ScheduleBuilder(eventName);
    }

    public NonRecurringScheduleBuilder once() {
        return new NonRecurringScheduleBuilder(this.eventName);
    }

    public DailyScheduleBuilder daily() {
        return new DailyScheduleBuilder(this.eventName);
    }

    public WeeklyScheduleBuilder weekly(DayOfWeek dayofWeek, DayOfWeek... daysOfWeek) {
        Set<DayOfWeek> daysOfWeekSet = new TreeSet<>(Arrays.asList(daysOfWeek));
        if (dayofWeek != null)
            daysOfWeekSet.add(dayofWeek);

        return new WeeklyScheduleBuilder(this.eventName, daysOfWeekSet);
    }

    public BiWeeklyScheduleBuilder biWeekly(DayOfWeek dayofWeek, DayOfWeek... daysOfWeek) {
        Set<DayOfWeek> daysOfWeekSet = new TreeSet<>(Arrays.asList(daysOfWeek));
        if (dayofWeek != null)
            daysOfWeekSet.add(dayofWeek);

        return new BiWeeklyScheduleBuilder(this.eventName, daysOfWeekSet);
    }

    public FixedDateMonthlyScheduleBuilder monthly(int dayOfMonth) {
        return new FixedDateMonthlyScheduleBuilder(this.eventName, dayOfMonth);
    }

    public FixedDayMonthlyScheduleBuilder monthly(Ordinal ordinal, DayOfWeek dayOfweek) {
        return new FixedDayMonthlyScheduleBuilder(this.eventName, ordinal, dayOfweek);
    }

    public FixedDateQuaterlyScheduleBuilder quaterly(int dayOfMonth) {
        return new FixedDateQuaterlyScheduleBuilder(this.eventName, dayOfMonth);
    }
}
