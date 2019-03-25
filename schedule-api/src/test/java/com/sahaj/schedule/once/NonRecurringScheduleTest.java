package com.sahaj.schedule.once;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import com.sahaj.schedule.BoundedSchedule;
import com.sahaj.schedule.builder.ScheduleBuilder;

public class NonRecurringScheduleTest {

    private BoundedSchedule schedule1;
    private BoundedSchedule schedule2;

    private final LocalDateTime SCHEDULE_DATE_12_FEB_2020 = LocalDateTime.of(2020, 2, 12, 0, 0);
    private final LocalDateTime DATE_11_FEB_2020 = LocalDateTime.of(2020, 2, 11, 0, 0);
    private final LocalDateTime DATE_13_FEB_2020 = LocalDateTime.of(2020, 2, 13, 0, 0);

    private final String EVENT_NAME_1 = "Event name 1";
    private final String EVENT_NAME_2 = "Event name 2";

    @Before
    public void setup() {
        LocalDateTime _12_Feb_2020 = LocalDateTime.of(2020, 2, 12, 0, 0);
        schedule1 = ScheduleBuilder.newSchedule(EVENT_NAME_1).once().on(_12_Feb_2020);

        schedule2 = ScheduleBuilder.newSchedule(EVENT_NAME_2).once().date(12).month(2).year(2020);
    }

    @Test
    public void eventNameIsStoredInSchedule() {
        scheduleHasEventName(schedule1, EVENT_NAME_1);
        scheduleHasEventName(schedule2, EVENT_NAME_2);
    }

    private void scheduleHasEventName(BoundedSchedule schedule, String expectedEvenName) {
        assertEquals(expectedEvenName, schedule.getEventName());
    }

    @Test
    public void startAndEndDateOfNonRepeatingScheduleIsSameAsScheduledDate() {
        startAndEndDateIsSameAsScheduledDate(schedule1, SCHEDULE_DATE_12_FEB_2020);
        startAndEndDateIsSameAsScheduledDate(schedule2, SCHEDULE_DATE_12_FEB_2020);
    }

    private void startAndEndDateIsSameAsScheduledDate(BoundedSchedule schedule, LocalDateTime expectedDate) {
        assertEquals(expectedDate, schedule.startDate());
        assertEquals(expectedDate, schedule.endDate());
    }

    @Test
    public void thereIsOnlyOneOccurrenceOfNonRepeatingSchedule() {
        scheduleHasOnlyOneOccurrence(schedule1, SCHEDULE_DATE_12_FEB_2020);
        scheduleHasOnlyOneOccurrence(schedule2, SCHEDULE_DATE_12_FEB_2020);
    }

    private void scheduleHasOnlyOneOccurrence(BoundedSchedule schedule, LocalDateTime expectedScheduleDate) {
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

    private void scheduleHasOneOccurrenceBeforeScheduleDate(BoundedSchedule schedule, LocalDateTime expectedScheduleDate) {
        assertEquals(Arrays.asList(expectedScheduleDate), schedule.getOccurrencesFrom(DATE_11_FEB_2020, 1));
        assertEquals(Arrays.asList(expectedScheduleDate), schedule.getOccurrencesFrom(DATE_11_FEB_2020, Integer.MAX_VALUE));
    }

    @Test
    public void nonRepeatingScheduleReturnsNoOccurenceAfterScheduleDate() {
        scheduleHasZeroOccurrenceAfterScheduleDate(schedule1);
        scheduleHasZeroOccurrenceAfterScheduleDate(schedule2);
    }

    private void scheduleHasZeroOccurrenceAfterScheduleDate(BoundedSchedule schedule) {
        assertEquals(Collections.emptyList(), schedule.getOccurrencesFrom(DATE_13_FEB_2020, 1));
    }

}
