package com.sahaj.schedule.biweekly;

import static com.sahaj.schedule.weekly.WeeklyScheduleBuilder.DAY_OF_WEEK_IS_MANDATORY;
import static com.sahaj.schedule.weekly.WeeklyScheduleBuilder.START_DATE_IS_MANDATORY;
import static com.sahaj.schedule.weekly.WeeklyScheduleBuilder.TIME_IS_MANDATORY;
import static java.time.DayOfWeek.MONDAY;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import com.sahaj.schedule.BoundedSchedule;
import com.sahaj.schedule.Schedule;
import com.sahaj.schedule.builder.ScheduleBuilder;

public class BiWeeklyScheduleBuilderTest {

    private BiWeeklyScheduleBuilder biweeklyMondayBuilder;
    private final String EVENT_NAME = "Event name";

    private final LocalDate START_TUES_01_JAN_2019 = LocalDate.of(2019, 1, 1);
    private final LocalDate END_MON_28_JAN_2019 = LocalDate.of(2019, 1, 28);
    private final LocalTime AT_10_AM = LocalTime.of(10, 0);

    private final LocalDateTime FIRST_OCCURRENCE_MON_07_JAN_2019_10AM = LocalDateTime.of(2019, 1, 7, 10, 0);
    private final LocalDateTime LAST_OCCURRENCE_MON_21_JAN_2019_10AM = LocalDateTime.of(2019, 1, 21, 10, 0);

    @Before
    public void setup() {
        biweeklyMondayBuilder = ScheduleBuilder.newSchedule(EVENT_NAME).biWeekly(MONDAY);
    }

    @Test
    public void createBoundedScheduleWithoutStartDate_ShouldThrowIllegalStateException() {
        try {
            biweeklyMondayBuilder.endingOn(END_MON_28_JAN_2019);
        } catch (IllegalStateException e) {
            assertEquals(START_DATE_IS_MANDATORY, e.getMessage());
        }
    }

    @Test
    public void createUnboundedScheduleWithoutStartDate_ShouldThrowIllegalStateException() {
        try {
            biweeklyMondayBuilder.neverEnding();
        } catch (IllegalStateException e) {
            assertEquals(START_DATE_IS_MANDATORY, e.getMessage());
        }
    }

    @Test
    public void createBoundedScheduleWithNullTime_ShouldThrowIllegalStateException() {
        try {
            biweeklyMondayBuilder.startingOn(START_TUES_01_JAN_2019, null).endingOn(END_MON_28_JAN_2019);
        } catch (IllegalStateException e) {
            assertEquals(TIME_IS_MANDATORY, e.getMessage());
        }
    }

    @Test
    public void createUnboundedScheduleWithNullTime_ShouldThrowIllegalStateException() {
        try {
            biweeklyMondayBuilder.startingOn(START_TUES_01_JAN_2019, null).neverEnding();
        } catch (IllegalStateException e) {
            assertEquals(TIME_IS_MANDATORY, e.getMessage());
        }
    }

    @Test
    public void createScheduleWithNullDayOfWeek_ShouldThrowIllegalStateException() {
        try {
            ScheduleBuilder.newSchedule(EVENT_NAME).weekly(null)
                .startingOn(START_TUES_01_JAN_2019, AT_10_AM)
                .endingOn(END_MON_28_JAN_2019);
        } catch (IllegalStateException e) {
            assertEquals(DAY_OF_WEEK_IS_MANDATORY, e.getMessage());
        }
    }

    @Test
    public void createBoundedSchedule_ScheduleCreatedWithStartDateEqualToFirstOccurrenceDateAndEndWithLastOccurrence() {
        BoundedSchedule TUES_01_TO_28_JAN_10AM =
            biweeklyMondayBuilder.startingOn(START_TUES_01_JAN_2019, AT_10_AM).endingOn(END_MON_28_JAN_2019);

        assertEquals(EVENT_NAME, TUES_01_TO_28_JAN_10AM.getEventName());

        assertEquals(FIRST_OCCURRENCE_MON_07_JAN_2019_10AM, TUES_01_TO_28_JAN_10AM.startDate());
        assertEquals(LAST_OCCURRENCE_MON_21_JAN_2019_10AM, TUES_01_TO_28_JAN_10AM.endDate());
    }

    @Test
    public void createUnboundedScheduleWithStartDateAndTime_ScheduleCreatedWithStartDateEqualToFirstOccurrence() {
        Schedule TUES_01_10AM_NOEND = biweeklyMondayBuilder.startingOn(START_TUES_01_JAN_2019, AT_10_AM).neverEnding();

        assertEquals(EVENT_NAME, TUES_01_10AM_NOEND.getEventName());
        assertEquals(FIRST_OCCURRENCE_MON_07_JAN_2019_10AM, TUES_01_10AM_NOEND.startDate());
    }
}
