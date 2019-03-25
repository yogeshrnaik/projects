package com.sahaj.schedule.daily;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import com.sahaj.schedule.BoundedSchedule;
import com.sahaj.schedule.Schedule;
import com.sahaj.schedule.builder.ScheduleBuilder;

public class DailyScheduleBuilderTest {

    private DailyScheduleBuilder builder;
    private final String EVENT_NAME = "Event name";

    private final LocalDateTime START_01_JAN_2019 = LocalDateTime.of(2019, 1, 1, 10, 0);
    private final LocalDateTime END_15_JAN_2019 = LocalDateTime.of(2019, 1, 15, 10, 0);

    @Before
    public void setup() {
        builder = ScheduleBuilder.newSchedule(EVENT_NAME).daily();
    }

    @Test
    public void createBoundedScheduleWithoutStartDate_ShouldThrowIllegalStateException() {
        try {
            builder.endingOn(END_15_JAN_2019);
        } catch (IllegalStateException e) {
            assertEquals("Start date is mandatory", e.getMessage());
        }
    }

    @Test
    public void createUnboundedScheduleWithoutStartDate_ShouldThrowIllegalStateException() {
        try {
            builder.neverEnding();
        } catch (IllegalStateException e) {
            assertEquals("Start date is mandatory", e.getMessage());
        }
    }

    @Test
    public void createBoundedScheduleWithStartAndEndDate_ScheduleShouldGetCreated() {
        BoundedSchedule schedule = builder.startingOn(START_01_JAN_2019).endingOn(END_15_JAN_2019);

        assertEquals(EVENT_NAME, schedule.getEventName());
        assertEquals(START_01_JAN_2019, schedule.startDate());
        assertEquals(END_15_JAN_2019, schedule.endDate());
    }

    @Test
    public void createUnboundedScheduleWithStartDate_ScheduleShouldGetCreated() {
        Schedule schedule = builder.startingOn(START_01_JAN_2019).neverEnding();

        assertEquals(EVENT_NAME, schedule.getEventName());
        assertEquals(START_01_JAN_2019, schedule.startDate());
    }
}
