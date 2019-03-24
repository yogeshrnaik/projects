package com.sahaj.schedule.builder;

public class ScheduleBuilder {

    private String eventName;

    private ScheduleBuilder(String eventName) {
        this.eventName = eventName;
    }

    public static ScheduleBuilder newSchedule(String eventName) {
        return new ScheduleBuilder(eventName);
    }

    public NonRepeatingScheduleBuilder once() {
        return new NonRepeatingScheduleBuilder(this.eventName);
    }

}
