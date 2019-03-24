package com.sahaj.schedule.builder;

public class ScheduleBuilder {

    protected String eventName;

    protected ScheduleBuilder(String eventName) {
        this.eventName = eventName;
    }

    public static ScheduleBuilder newSchedule(String eventName) {
        return new ScheduleBuilder(eventName);
    }

    public NonRepeatingScheduleBuilder once() {
        return new NonRepeatingScheduleBuilder(this.eventName);
    }

    public DailyScheduleBuilder daily() {
        return new DailyScheduleBuilder(this.eventName);
    }

}
