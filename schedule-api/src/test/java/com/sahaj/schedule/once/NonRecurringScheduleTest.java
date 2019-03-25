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

    private BoundedSchedule onceOn12Feb2020;
    private BoundedSchedule onceOn13Feb2020;

    private final LocalDateTime SCHEDULE_DATE_12_FEB_2020 = LocalDateTime.of(2020, 2, 12, 0, 0);
    private final LocalDateTime SCHEDULE_DATE_13_FEB_2020 = LocalDateTime.of(2020, 2, 13, 0, 0);

    private final LocalDateTime DATE_11_FEB_2020 = LocalDateTime.of(2020, 2, 11, 0, 0);
    private final LocalDateTime DATE_13_FEB_2020 = LocalDateTime.of(2020, 2, 13, 0, 0);
    private final LocalDateTime DATE_14_FEB_2020 = LocalDateTime.of(2020, 2, 14, 0, 0);

    private final String EVENT_NAME_1 = "Event name 1";
    private final String EVENT_NAME_2 = "Event name 2";

    @Before
    public void setup() {
        LocalDateTime _12_Feb_2020 = LocalDateTime.of(2020, 2, 12, 0, 0);
        onceOn12Feb2020 = ScheduleBuilder.newSchedule(EVENT_NAME_1).once().on(_12_Feb_2020);

        onceOn13Feb2020 = ScheduleBuilder.newSchedule(EVENT_NAME_2).once().date(13).month(2).year(2020);
    }

    @Test
    public void eventNameIsStoredInSchedule() {
        scheduleHasEventName(onceOn12Feb2020, EVENT_NAME_1);
        scheduleHasEventName(onceOn13Feb2020, EVENT_NAME_2);
    }

    private void scheduleHasEventName(BoundedSchedule schedule, String expectedEvenName) {
        assertEquals(expectedEvenName, schedule.getEventName());
    }

    @Test
    public void startAndEndDateOfNonRecurringScheduleIsSameAsScheduledDate() {
        startAndEndDateIsSameAsScheduledDate(onceOn12Feb2020, SCHEDULE_DATE_12_FEB_2020);
        startAndEndDateIsSameAsScheduledDate(onceOn13Feb2020, SCHEDULE_DATE_13_FEB_2020);
    }

    private void startAndEndDateIsSameAsScheduledDate(BoundedSchedule schedule, LocalDateTime expectedDate) {
        assertEquals(expectedDate, schedule.startDate());
        assertEquals(expectedDate, schedule.endDate());
    }

    @Test
    public void thereIsOnlyOneOccurrenceOfNonRecurringSchedule() {
        scheduleHasOnlyOneOccurrence(onceOn12Feb2020, SCHEDULE_DATE_12_FEB_2020);
        scheduleHasOnlyOneOccurrence(onceOn13Feb2020, SCHEDULE_DATE_13_FEB_2020);
    }

    private void scheduleHasOnlyOneOccurrence(BoundedSchedule schedule, LocalDateTime expectedScheduleDate) {
        assertEquals(Arrays.asList(expectedScheduleDate), schedule.getOccurrences(1));
        assertEquals(Arrays.asList(expectedScheduleDate), schedule.getOccurrences(Integer.MAX_VALUE));

        assertEquals(Arrays.asList(expectedScheduleDate), schedule.getAllOccurrences());
        assertEquals(1, schedule.getNumberOfOccurences());
    }

    @Test
    public void nonRecurringScheduleReturnsOccurenceBeforeScheduleDate() {
        scheduleHasOneOccurrenceBeforeScheduleDate(onceOn12Feb2020, SCHEDULE_DATE_12_FEB_2020);
        scheduleHasOneOccurrenceBeforeScheduleDate(onceOn13Feb2020, SCHEDULE_DATE_13_FEB_2020);
    }

    @Test
    public void nonRecurringScheduleReturnsOneOccurenceOnScheduleDate() {
        nonRecurringScheduleReturnsOneOccurenceOnScheduleDate(onceOn12Feb2020, SCHEDULE_DATE_12_FEB_2020);
        nonRecurringScheduleReturnsOneOccurenceOnScheduleDate(onceOn13Feb2020, SCHEDULE_DATE_13_FEB_2020);
    }

    private void nonRecurringScheduleReturnsOneOccurenceOnScheduleDate(BoundedSchedule schedule, LocalDateTime scheduledDate) {
        assertEquals(Arrays.asList(scheduledDate),
            schedule.getOccurrencesFrom(scheduledDate, 1));
        assertEquals(Arrays.asList(scheduledDate),
            schedule.getOccurrencesFrom(scheduledDate, Integer.MAX_VALUE));
    }

    private void scheduleHasOneOccurrenceBeforeScheduleDate(BoundedSchedule schedule, LocalDateTime expectedScheduleDate) {
        assertEquals(Arrays.asList(expectedScheduleDate), schedule.getOccurrencesFrom(DATE_11_FEB_2020, 1));
        assertEquals(Arrays.asList(expectedScheduleDate), schedule.getOccurrencesFrom(DATE_11_FEB_2020, Integer.MAX_VALUE));
    }

    @Test
    public void nonRecurringScheduleReturnsNoOccurenceAfterScheduleDate() {
        scheduleHasZeroOccurrenceAfterScheduleDate(onceOn12Feb2020, DATE_13_FEB_2020);
        scheduleHasZeroOccurrenceAfterScheduleDate(onceOn13Feb2020, DATE_14_FEB_2020);
    }

    private void scheduleHasZeroOccurrenceAfterScheduleDate(BoundedSchedule schedule, LocalDateTime afterScheduleDate) {
        assertEquals(Collections.emptyList(), schedule.getOccurrencesFrom(afterScheduleDate, 1));
    }

}
