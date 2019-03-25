package com.sahaj.schedule.builder;

import com.sahaj.schedule.daily.DailyScheduleBuilder;
import com.sahaj.schedule.once.NonRecurringScheduleBuilder;

public class ScheduleBuilder {

    protected String eventName;

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

}
