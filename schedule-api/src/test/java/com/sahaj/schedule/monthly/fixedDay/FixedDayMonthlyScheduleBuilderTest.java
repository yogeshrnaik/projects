package com.sahaj.schedule.monthly.fixedDay;

import static com.sahaj.schedule.monthly.fixedDay.FixedDayMonthlyScheduleBuilder.DAY_OF_WEEK_IS_MANDATORY;
import static com.sahaj.schedule.monthly.fixedDay.FixedDayMonthlyScheduleBuilder.ORDINAL_IS_MANDATORY;
import static com.sahaj.schedule.monthly.fixedDay.FixedDayMonthlyScheduleBuilder.START_DATE_IS_MANDATORY;
import static com.sahaj.schedule.monthly.fixedDay.FixedDayMonthlyScheduleBuilder.TIME_IS_MANDATORY;
import static java.time.DayOfWeek.MONDAY;
import static org.junit.Assert.assertEquals;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import com.sahaj.schedule.BoundedSchedule;
import com.sahaj.schedule.Schedule;
import com.sahaj.schedule.builder.ScheduleBuilder;

public class FixedDayMonthlyScheduleBuilderTest {

    private FixedDayMonthlyScheduleBuilder monthlyBuilder;
    private final String EVENT_NAME = "Event name";

    private final LocalDate START_TUES_01_JAN_2019 = LocalDate.of(2019, 1, 1);
    private final LocalDate END_31_JUL_2019 = LocalDate.of(2019, 7, 31);
    private final LocalTime AT_10_AM = LocalTime.of(10, 0);

    private final LocalDateTime FIRST_OCCURRENCE_MON_28_JAN_2019_10AM = LocalDateTime.of(2019, 1, 28, 10, 0);
    private final LocalDateTime LAST_OCCURRENCE_MON_29_JUL_2019_10AM = LocalDateTime.of(2019, 7, 29, 10, 0);

    @Before
    public void setup() {
        monthlyBuilder = ScheduleBuilder.newSchedule(EVENT_NAME).monthly(Ordinal.LAST, DayOfWeek.MONDAY);
    }

    @Test
    public void createBoundedScheduleWithoutStartDate_ShouldThrowIllegalStateException() {
        try {
            monthlyBuilder.endingOn(END_31_JUL_2019);
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
            monthlyBuilder.startingOn(START_TUES_01_JAN_2019, null).endingOn(END_31_JUL_2019);
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
    public void createScheduleWithNullOrdinalShouldThrowIllegalStateException() {
        try {
            ScheduleBuilder.newSchedule(EVENT_NAME).monthly(null, MONDAY)
                .startingOn(START_TUES_01_JAN_2019, AT_10_AM)
                .endingOn(END_31_JUL_2019);
        } catch (IllegalStateException e) {
            assertEquals(ORDINAL_IS_MANDATORY, e.getMessage());
        }
    }

    @Test
    public void createScheduleWithNullDayOfWeekShouldThrowIllegalStateException() {
        try {
            ScheduleBuilder.newSchedule(EVENT_NAME).monthly(Ordinal.FIRST, null)
                .startingOn(START_TUES_01_JAN_2019, AT_10_AM)
                .endingOn(END_31_JUL_2019);
        } catch (IllegalStateException e) {
            assertEquals(DAY_OF_WEEK_IS_MANDATORY, e.getMessage());
        }
    }

    @Test
    public void createBoundedSchedule_ScheduleCreatedWithStartDateEqualToFirstOccurrenceDateAndEndWithLastOccurrence() {
        BoundedSchedule _01_JAN_TO_31_JUL_10AM =
            monthlyBuilder.startingOn(START_TUES_01_JAN_2019, AT_10_AM).endingOn(END_31_JUL_2019);

        assertEquals(EVENT_NAME, _01_JAN_TO_31_JUL_10AM.getEventName());
        assertEquals(FIRST_OCCURRENCE_MON_28_JAN_2019_10AM, _01_JAN_TO_31_JUL_10AM.startDate());
        assertEquals(LAST_OCCURRENCE_MON_29_JUL_2019_10AM, _01_JAN_TO_31_JUL_10AM.endDate());
    }

    @Test
    public void createUnboundedScheduleWithStartDateAndTime_ScheduleCreatedWithStartDateEqualToFirstOccurrence() {
        Schedule _01_JAN_10AM_NOEND = monthlyBuilder.startingOn(START_TUES_01_JAN_2019, AT_10_AM).neverEnding();

        assertEquals(EVENT_NAME, _01_JAN_10AM_NOEND.getEventName());
        assertEquals(FIRST_OCCURRENCE_MON_28_JAN_2019_10AM, _01_JAN_10AM_NOEND.startDate());
    }

}
