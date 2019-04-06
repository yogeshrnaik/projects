package com.sahaj.schedule.monthly.fixedDate;

import static com.sahaj.schedule.monthly.fixedDate.FixedDateMonthlyScheduleBuilder.DATE_OF_MONTH_IS_INVALID;
import static com.sahaj.schedule.monthly.fixedDate.FixedDateMonthlyScheduleBuilder.START_DATE_IS_MANDATORY;
import static com.sahaj.schedule.monthly.fixedDate.FixedDateMonthlyScheduleBuilder.TIME_IS_MANDATORY;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import com.sahaj.schedule.BoundedSchedule;
import com.sahaj.schedule.Schedule;
import com.sahaj.schedule.builder.ScheduleBuilder;
import com.sahaj.schedule.monthly.fixedDate.FixedDateMonthlyScheduleBuilder;

public class FixedDateMonthlyScheduleBuilderTest {

    private FixedDateMonthlyScheduleBuilder monthlyBuilder;
    private final String EVENT_NAME = "Event name";

    private final LocalDate START_TUES_01_JAN_2019 = LocalDate.of(2019, 1, 1);
    private final LocalDate END_30_JUN_2019 = LocalDate.of(2019, 7, 30);
    private final LocalTime AT_10_AM = LocalTime.of(10, 0);

    private final LocalDateTime FIRST_OCCURRENCE_31_JAN_2019_10AM = LocalDateTime.of(2019, 1, 31, 10, 0);
    private final LocalDateTime LAST_OCCURRENCE_30_JUN_2019_10AM = LocalDateTime.of(2019, 6, 30, 10, 0);

    @Before
    public void setup() {
        monthlyBuilder = ScheduleBuilder.newSchedule(EVENT_NAME).monthly(31);
    }

    @Test
    public void createBoundedScheduleWithoutStartDate_ShouldThrowIllegalStateException() {
        try {
            monthlyBuilder.endingOn(END_30_JUN_2019);
        } catch (IllegalStateException e) {
            assertEquals(START_DATE_IS_MANDATORY, e.getMessage());
        }
    }

    @Test
    public void createUnboundedScheduleWithoutStartDate_ShouldThrowIllegalStateException() {
        try {
            monthlyBuilder.neverEnding();
        } catch (IllegalStateException e) {
            assertEquals(START_DATE_IS_MANDATORY, e.getMessage());
        }
    }

    @Test
    public void createBoundedScheduleWithNullTime_ShouldThrowIllegalStateException() {
        try {
            monthlyBuilder.startingOn(START_TUES_01_JAN_2019, null).endingOn(END_30_JUN_2019);
        } catch (IllegalStateException e) {
            assertEquals(TIME_IS_MANDATORY, e.getMessage());
        }
    }

    @Test
    public void createUnboundedScheduleWithNullTime_ShouldThrowIllegalStateException() {
        try {
            monthlyBuilder.startingOn(START_TUES_01_JAN_2019, null).neverEnding();
        } catch (IllegalStateException e) {
            assertEquals(TIME_IS_MANDATORY, e.getMessage());
        }
    }

    @Test
    public void createScheduleWithInvalidDayOfMonth_ShouldThrowIllegalStateException() {
        try {
            ScheduleBuilder.newSchedule(EVENT_NAME).monthly(32)
                .startingOn(START_TUES_01_JAN_2019, AT_10_AM)
                .endingOn(END_30_JUN_2019);
        } catch (IllegalStateException e) {
            assertEquals(DATE_OF_MONTH_IS_INVALID, e.getMessage());
        }
    }

    @Test
    public void createBoundedSchedule_ScheduleCreatedWithStartDateEqualToFirstOccurrenceDateAndEndWithLastOccurrence() {
        BoundedSchedule _01_JAN_TO_30_JUN_10AM =
            monthlyBuilder.startingOn(START_TUES_01_JAN_2019, AT_10_AM).endingOn(END_30_JUN_2019);

        assertEquals(EVENT_NAME, _01_JAN_TO_30_JUN_10AM.getEventName());
        assertEquals(FIRST_OCCURRENCE_31_JAN_2019_10AM, _01_JAN_TO_30_JUN_10AM.startDate());
        assertEquals(LAST_OCCURRENCE_30_JUN_2019_10AM, _01_JAN_TO_30_JUN_10AM.endDate());
    }

    @Test
    public void createUnboundedScheduleWithStartDateAndTime_ScheduleCreatedWithStartDateEqualToFirstOccurrence() {
        Schedule _01_JAN_10AM_NOEND = monthlyBuilder.startingOn(START_TUES_01_JAN_2019, AT_10_AM).neverEnding();

        assertEquals(EVENT_NAME, _01_JAN_10AM_NOEND.getEventName());
        assertEquals(FIRST_OCCURRENCE_31_JAN_2019_10AM, _01_JAN_10AM_NOEND.startDate());
    }

}
