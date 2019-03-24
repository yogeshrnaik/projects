package com.sahaj.schedule;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import com.sahaj.schedule.builder.ScheduleBuilder;

public class DailyUnboundedScheduleTest {

    private BoundedSchedule boundedDaily;
    private Schedule unboundedDaily;

    private final String EVENT_NAME_1 = "Event name 1";
    private final String EVENT_NAME_2 = "Event name 2";

    private final LocalDateTime START_01_JAN_2019 = LocalDateTime.of(2019, 1, 1, 0, 0);
    private final LocalDateTime END_15_FEB_2019 = LocalDateTime.of(2019, 1, 15, 0, 0);

    @Before
    public void setup() {
        boundedDaily = ScheduleBuilder.newSchedule(EVENT_NAME_1).daily()
            .startingOn(START_01_JAN_2019)
            .endingOn(END_15_FEB_2019);

        unboundedDaily = ScheduleBuilder.newSchedule(EVENT_NAME_2).daily()
            .startingOn(START_01_JAN_2019).neverEnding();
    }

    @Test
    public void eventNameIsStoredInSchedule() {
        scheduleHasEventName(boundedDaily, EVENT_NAME_1);
        scheduleHasEventName(unboundedDaily, EVENT_NAME_2);
    }

    private void scheduleHasEventName(Schedule schedule, String expectedEvenName) {
        assertEquals(expectedEvenName, schedule.getEventName());
    }

    @Test
    public void testStartAndEndDatesOfBoundedDailySchedule() {
        assertEquals(START_01_JAN_2019, boundedDaily.startDate());
        assertEquals(END_15_FEB_2019, boundedDaily.endDate());
    }

    @Test
    public void testStartAndEndDatesOfUnboundedDailySchedule() {
        assertEquals(START_01_JAN_2019, unboundedDaily.startDate());
    }

}
