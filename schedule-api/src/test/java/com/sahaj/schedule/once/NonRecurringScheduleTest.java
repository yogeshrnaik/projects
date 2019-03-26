package com.sahaj.schedule.once;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        onceOn13Feb2020_7pm = ScheduleBuilder.newSchedule(EVENT_NAME_2).once().on(SCHEDULE_13_FEB_2020, AT_7PM);
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
        List<LocalDateTime> expectedOccurrences = Arrays.asList(expectedScheduleDate);
        assertEquals(expectedOccurrences, schedule.getOccurrences(1));
        assertEquals(expectedOccurrences, schedule.getOccurrences(Integer.MAX_VALUE));

        assertEquals(expectedOccurrences, schedule.getAllOccurrences());
        assertEquals(1, schedule.getNumberOfOccurences());
    }

    @Test
    public void getOccurrencesFromDateBeforeScheduleDate_ReturnsOneOccurenceOfScheduleDate() {
        scheduleHasOneOccurrence(onceOn12Feb2020_7pm, DATE_11_FEB_2020_7PM, SCHEDULE_12_FEB_2020_7PM);
        scheduleHasOneOccurrence(onceOn13Feb2020_7pm, DATE_11_FEB_2020_7PM, SCHEDULE_13_FEB_2020_7PM);
    }

    private void scheduleHasOneOccurrence(BoundedSchedule schedule, LocalDateTime fromDate,
        LocalDateTime expectedScheduleDate) {
        assertEquals(Arrays.asList(expectedScheduleDate), schedule.getOccurrencesFrom(fromDate, 1));
        assertEquals(Arrays.asList(expectedScheduleDate), schedule.getOccurrencesFrom(fromDate, Integer.MAX_VALUE));
    }

    @Test
    public void getOccurrencesFromDateEqualToScheduleDate_ReturnsOneOccurenceOfScheduleDate() {
        scheduleHasOneOccurrence(onceOn12Feb2020_7pm, SCHEDULE_12_FEB_2020_7PM, SCHEDULE_12_FEB_2020_7PM);
        scheduleHasOneOccurrence(onceOn13Feb2020_7pm, SCHEDULE_12_FEB_2020_7PM, SCHEDULE_13_FEB_2020_7PM);
    }

    @Test
    public void getOccurrencesFromDateAfterScheduleDate_ReturnsNoOccurence() {
        scheduleHasZeroOccurrenceAfterScheduleDate(onceOn12Feb2020_7pm, DATE_13_FEB_2020_7PM);
        scheduleHasZeroOccurrenceAfterScheduleDate(onceOn13Feb2020_7pm, DATE_14_FEB_2020_7PM);
    }

    private void scheduleHasZeroOccurrenceAfterScheduleDate(BoundedSchedule schedule, LocalDateTime afterScheduleDate) {
        assertEquals(Collections.emptyList(), schedule.getOccurrencesFrom(afterScheduleDate, 1));
    }

}
