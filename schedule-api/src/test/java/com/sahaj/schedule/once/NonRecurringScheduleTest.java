package com.sahaj.schedule.once;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import com.sahaj.schedule.BoundedSchedule;
import com.sahaj.schedule.builder.ScheduleBuilder;

public class NonRecurringScheduleTest {

    private BoundedSchedule onceOn12Feb2020_7pm;
    private BoundedSchedule onceOn13Feb2020_7pm;

    private final LocalDate SCHEDULE_12_FEB_2020 = LocalDate.of(2020, 2, 12);
    private final LocalDate SCHEDULE_13_FEB_2020 = LocalDate.of(2020, 2, 13);

    private final LocalTime AT_7PM = LocalTime.of(19, 0);

    private final LocalDateTime SCHEDULE_12_FEB_2020_7PM = SCHEDULE_12_FEB_2020.atTime(AT_7PM);
    private final LocalDateTime SCHEDULE_13_FEB_2020_7PM = SCHEDULE_13_FEB_2020.atTime(AT_7PM);

    private final LocalDateTime DATE_11_FEB_2020_7PM = LocalDate.of(2020, 2, 11).atTime(AT_7PM);
    private final LocalDateTime DATE_13_FEB_2020_7PM = LocalDate.of(2020, 2, 13).atTime(AT_7PM);
    private final LocalDateTime DATE_14_FEB_2020_7PM = LocalDate.of(2020, 2, 14).atTime(AT_7PM);

    private final String EVENT_NAME_1 = "Event name 1";
    private final String EVENT_NAME_2 = "Event name 2";

    @Before
    public void setup() {
        onceOn12Feb2020_7pm = ScheduleBuilder.newSchedule(EVENT_NAME_1).once().on(SCHEDULE_12_FEB_2020, AT_7PM);
        onceOn13Feb2020_7pm = ScheduleBuilder.newSchedule(EVENT_NAME_2).once().date(13).month(2).year(2020).at(AT_7PM);
    }

    @Test
    public void eventNameIsStoredInSchedule() {
        scheduleHasEventName(onceOn12Feb2020_7pm, EVENT_NAME_1);
        scheduleHasEventName(onceOn13Feb2020_7pm, EVENT_NAME_2);
    }

    private void scheduleHasEventName(BoundedSchedule schedule, String expectedEvenName) {
        assertEquals(expectedEvenName, schedule.getEventName());
    }

    @Test
    public void startAndEndDateOfNonRecurringScheduleIsSameAsScheduledDate() {
        startAndEndDateIsSameAsScheduledDate(onceOn12Feb2020_7pm, SCHEDULE_12_FEB_2020_7PM);
        startAndEndDateIsSameAsScheduledDate(onceOn13Feb2020_7pm, SCHEDULE_13_FEB_2020_7PM);
    }

    private void startAndEndDateIsSameAsScheduledDate(BoundedSchedule schedule, LocalDateTime expectedDate) {
        assertEquals(expectedDate, schedule.startDate());
        assertEquals(expectedDate, schedule.endDate());
    }

    @Test
    public void thereIsOnlyOneOccurrenceOfNonRecurringSchedule() {
        scheduleHasOnlyOneOccurrence(onceOn12Feb2020_7pm, SCHEDULE_12_FEB_2020_7PM);
        scheduleHasOnlyOneOccurrence(onceOn13Feb2020_7pm, SCHEDULE_13_FEB_2020_7PM);
    }

    private void scheduleHasOnlyOneOccurrence(BoundedSchedule schedule, LocalDateTime expectedScheduleDate) {
        assertEquals(Arrays.asList(expectedScheduleDate), schedule.getOccurrences(1));
        assertEquals(Arrays.asList(expectedScheduleDate), schedule.getOccurrences(Integer.MAX_VALUE));

        assertEquals(Arrays.asList(expectedScheduleDate), schedule.getAllOccurrences());
        assertEquals(1, schedule.getNumberOfOccurences());
    }

    @Test
    public void nonRecurringScheduleReturnsOccurenceBeforeScheduleDate() {
        scheduleHasOneOccurrenceBeforeScheduleDate(onceOn12Feb2020_7pm, SCHEDULE_12_FEB_2020_7PM);
        scheduleHasOneOccurrenceBeforeScheduleDate(onceOn13Feb2020_7pm, SCHEDULE_13_FEB_2020_7PM);
    }

    @Test
    public void nonRecurringScheduleReturnsOneOccurenceOnScheduleDate() {
        nonRecurringScheduleReturnsOneOccurenceOnScheduleDate(onceOn12Feb2020_7pm, SCHEDULE_12_FEB_2020_7PM);
        nonRecurringScheduleReturnsOneOccurenceOnScheduleDate(onceOn13Feb2020_7pm, SCHEDULE_13_FEB_2020_7PM);
    }

    private void nonRecurringScheduleReturnsOneOccurenceOnScheduleDate(BoundedSchedule schedule, LocalDateTime scheduledDate) {
        assertEquals(Arrays.asList(scheduledDate),
            schedule.getOccurrencesFrom(scheduledDate, 1));
        assertEquals(Arrays.asList(scheduledDate),
            schedule.getOccurrencesFrom(scheduledDate, Integer.MAX_VALUE));
    }

    private void scheduleHasOneOccurrenceBeforeScheduleDate(BoundedSchedule schedule, LocalDateTime expectedScheduleDate) {
        assertEquals(Arrays.asList(expectedScheduleDate), schedule.getOccurrencesFrom(DATE_11_FEB_2020_7PM, 1));
        assertEquals(Arrays.asList(expectedScheduleDate), schedule.getOccurrencesFrom(DATE_11_FEB_2020_7PM, Integer.MAX_VALUE));
    }

    @Test
    public void nonRecurringScheduleReturnsNoOccurenceAfterScheduleDate() {
        scheduleHasZeroOccurrenceAfterScheduleDate(onceOn12Feb2020_7pm, DATE_13_FEB_2020_7PM);
        scheduleHasZeroOccurrenceAfterScheduleDate(onceOn13Feb2020_7pm, DATE_14_FEB_2020_7PM);
    }

    private void scheduleHasZeroOccurrenceAfterScheduleDate(BoundedSchedule schedule, LocalDateTime afterScheduleDate) {
        assertEquals(Collections.emptyList(), schedule.getOccurrencesFrom(afterScheduleDate, 1));
    }

}
