package com.sahaj.schedule.daily;

import static com.sahaj.schedule.daily.DailyScheduleBuilder.START_DATE_IS_MANDATORY;
import static com.sahaj.schedule.daily.DailyScheduleBuilder.TIME_IS_MANDATORY;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import com.sahaj.schedule.BoundedSchedule;
import com.sahaj.schedule.Schedule;
import com.sahaj.schedule.builder.ScheduleBuilder;

public class DailyScheduleBuilderTest {

    private DailyScheduleBuilder builder;
    private final String EVENT_NAME = "Event name";

    private final LocalDate START_01_JAN_2019 = LocalDate.of(2019, 1, 1);
    private final LocalDate END_15_JAN_2019 = LocalDate.of(2019, 1, 15);
    private final LocalTime AT_10_AM = LocalTime.of(10, 0);

    private final LocalDateTime START_01_JAN_2019_10AM = START_01_JAN_2019.atTime(AT_10_AM);
    private final LocalDateTime END_15_JAN_2019_10AM = END_15_JAN_2019.atTime(AT_10_AM);

    @Before
    public void setup() {
        builder = ScheduleBuilder.newSchedule(EVENT_NAME).daily();
    }

    @Test
    public void createBoundedScheduleWithoutStartDate_ShouldThrowIllegalStateException() {
        try {
            builder.endingOn(END_15_JAN_2019);
        } catch (IllegalStateException e) {
            assertEquals(START_DATE_IS_MANDATORY, e.getMessage());
        }
    }

    @Test
    public void createUnboundedScheduleWithoutStartDate_ShouldThrowIllegalStateException() {
        try {
            builder.neverEnding();
        } catch (IllegalStateException e) {
            assertEquals(START_DATE_IS_MANDATORY, e.getMessage());
        }
    }

    @Test
    public void createBoundedScheduleWithNullTime_ShouldThrowIllegalStateException() {
        try {
            builder.startingOn(START_01_JAN_2019, null).endingOn(END_15_JAN_2019);
        } catch (IllegalStateException e) {
            assertEquals(TIME_IS_MANDATORY, e.getMessage());
        }
    }

    @Test
    public void createUnboundedScheduleWithNullTime_ShouldThrowIllegalStateException() {
        try {
            builder.startingOn(START_01_JAN_2019, null).neverEnding();
        } catch (IllegalStateException e) {
            assertEquals(TIME_IS_MANDATORY, e.getMessage());
        }
    }

    @Test
    public void createBoundedScheduleWithStartAndEndDateAndTime_ScheduleShouldGetCreated() {
        BoundedSchedule schedule = builder.startingOn(START_01_JAN_2019, AT_10_AM).endingOn(END_15_JAN_2019);

        assertEquals(EVENT_NAME, schedule.getEventName());
        assertEquals(START_01_JAN_2019_10AM, schedule.startDate());
        assertEquals(END_15_JAN_2019_10AM, schedule.endDate());
    }

    @Test
    public void createUnboundedScheduleWithStartDateAndTime_ScheduleShouldGetCreated() {
        Schedule schedule = builder.startingOn(START_01_JAN_2019, AT_10_AM).neverEnding();

        assertEquals(EVENT_NAME, schedule.getEventName());
        assertEquals(START_01_JAN_2019_10AM, schedule.startDate());
    }
}
