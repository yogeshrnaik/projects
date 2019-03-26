package com.sahaj.schedule.once;

import static org.junit.Assert.assertEquals;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import com.sahaj.schedule.BoundedSchedule;
import com.sahaj.schedule.builder.ScheduleBuilder;

public class NonRecurringScheduleBuilderTest {

    private NonRecurringScheduleBuilder builder;
    private final String EVENT_NAME = "Event name";

    private final LocalDate SCHEDULE_12_FEB_2020 = LocalDate.of(2020, 2, 12);
    private final LocalTime AT_10AM = LocalTime.of(10, 0);
    private final LocalDateTime SCHEDULE_12_FEB_2020_10AM = SCHEDULE_12_FEB_2020.atTime(AT_10AM);

    @Before
    public void setup() {
        builder = ScheduleBuilder.newSchedule(EVENT_NAME).once();
    }

    @Test
    public void createNonRecurringScheduleWithDate_ScheduleShouldGetCreated() {
        BoundedSchedule schedule = builder.on(SCHEDULE_12_FEB_2020, AT_10AM);

        assertEquals(EVENT_NAME, schedule.getEventName());
        assertEquals(SCHEDULE_12_FEB_2020_10AM, schedule.startDate());
        assertEquals(SCHEDULE_12_FEB_2020_10AM, schedule.endDate());
    }

    @Test(expected = IllegalStateException.class)
    public void createNonRecurringScheduleWithoutDateOfMonth_ShouldThrowIllegalStateException() {
        try {
            builder.month(1).year(2019).at(AT_10AM);
        } catch (IllegalStateException e) {
            assertEquals("Date of month is mandatory", e.getMessage());
            throw e;
        }
    }

    @Test(expected = IllegalStateException.class)
    public void createNonRecurringScheduleWithoutMonth_ShouldThrowIllegalStateException() {
        try {
            builder.date(1).year(2019).at(AT_10AM);
        } catch (IllegalStateException e) {
            assertEquals("Month is mandatory", e.getMessage());
            throw e;
        }
    }

    @Test(expected = DateTimeException.class)
    public void createNonRecurringScheduleWithInvalidDate_ShouldThrowDateTimeException() {
        try {
            builder.date(32).month(5).year(2019).at(AT_10AM);
        } catch (DateTimeException e) {
            throw e;
        }
    }

    @Test(expected = DateTimeException.class)
    public void createNonRecurringScheduleWithInvalidMonth_ShouldThrowDateTimeException() {
        try {
            builder.date(1).month(0).year(2019).at(AT_10AM);
        } catch (DateTimeException e) {
            throw e;
        }
    }
}
