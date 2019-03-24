package com.sahaj.schedule;

import static org.junit.Assert.assertEquals;

import java.time.Month;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import com.sahaj.schedule.builder.ScheduleBuilder;

public class NonRepeatingScheduleTest {

    private Schedule schedule1;
    private Schedule schedule2;

    private final Date SCHEDULE_DATE_12_FEB_2020 = new GregorianCalendar(2020, Calendar.FEBRUARY, 12).getTime();
    private final Date DATE_11_FEB_2020 = new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime();
    private final Date DATE_13_FEB_2020 = new GregorianCalendar(2020, Calendar.FEBRUARY, 13).getTime();

    private final String EVENT_NAME_1 = "Event name 1";
    private final String EVENT_NAME_2 = "Event name 2";

    @Before
    public void setup() {
        Date _12_Feb_2020 = new GregorianCalendar(2020, Calendar.FEBRUARY, 12).getTime();
        schedule1 = ScheduleBuilder.newSchedule(EVENT_NAME_1).once().on(_12_Feb_2020);

        schedule2 = ScheduleBuilder.newSchedule(EVENT_NAME_2).once().date(12).month(Month.FEBRUARY).year(2020);
    }

    @Test
    public void eventNameIsStoredInSchedule() {
        scheduleHasEventName(schedule1, EVENT_NAME_1);
        scheduleHasEventName(schedule2, EVENT_NAME_2);
    }

    private void scheduleHasEventName(Schedule schedule, String expectedEvenName) {
        assertEquals(expectedEvenName, schedule.getEventName());
    }

    @Test
    public void startAndEndDateOfNonRepeatingScheduleIsSameAsScheduledDate() {
        startAndEndDateIsSameAsScheduledDate(schedule1, SCHEDULE_DATE_12_FEB_2020);
        startAndEndDateIsSameAsScheduledDate(schedule2, SCHEDULE_DATE_12_FEB_2020);
    }

    private void startAndEndDateIsSameAsScheduledDate(Schedule schedule, Date expectedDate) {
        assertEquals(expectedDate, schedule.startDate());
        assertEquals(expectedDate, schedule.endDate());
    }

    @Test
    public void thereIsOnlyOneOccurrenceOfNonRepeatingSchedule() {
        scheduleHasOnlyOneOccurrence(schedule1, SCHEDULE_DATE_12_FEB_2020);
        scheduleHasOnlyOneOccurrence(schedule2, SCHEDULE_DATE_12_FEB_2020);
    }

    private void scheduleHasOnlyOneOccurrence(Schedule schedule, Date expectedScheduleDate) {
        assertEquals(Arrays.asList(expectedScheduleDate), schedule.getOccurrences(1));
        assertEquals(Arrays.asList(expectedScheduleDate), schedule.getOccurrences(Integer.MAX_VALUE));

        assertEquals(Arrays.asList(expectedScheduleDate), schedule.getAllOccurrences());
        assertEquals(1, schedule.getNumberOfOccurences());
    }

    @Test
    public void nonRepeatingScheduleReturnsOccurenceBeforeScheduleDate() {
        scheduleHasOneOccurrenceBeforeScheduleDate(schedule1, SCHEDULE_DATE_12_FEB_2020);
        scheduleHasOneOccurrenceBeforeScheduleDate(schedule2, SCHEDULE_DATE_12_FEB_2020);
    }

    private void scheduleHasOneOccurrenceBeforeScheduleDate(Schedule schedule, Date expectedScheduleDate) {
        assertEquals(Arrays.asList(expectedScheduleDate), schedule.getOccurrencesFrom(DATE_11_FEB_2020, 1));
        assertEquals(Arrays.asList(expectedScheduleDate), schedule.getOccurrencesFrom(DATE_11_FEB_2020, Integer.MAX_VALUE));
    }

    @Test
    public void nonRepeatingScheduleReturnsNoOccurenceAfterScheduleDate() {
        scheduleHasZeroOccurrenceAfterScheduleDate(schedule1);
        scheduleHasZeroOccurrenceAfterScheduleDate(schedule2);
    }

    private void scheduleHasZeroOccurrenceAfterScheduleDate(Schedule schedule) {
        assertEquals(Collections.emptyList(), schedule.getOccurrencesFrom(DATE_13_FEB_2020, 1));
    }

}
