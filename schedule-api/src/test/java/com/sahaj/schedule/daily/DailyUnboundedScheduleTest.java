package com.sahaj.schedule.daily;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sahaj.schedule.Schedule;
import com.sahaj.schedule.builder.ScheduleBuilder;

public class DailyUnboundedScheduleTest {

    private Schedule unboundedDaily;

    private final String EVENT_NAME_1 = "Event name 1";

    private final LocalDate START_01_JAN_2019 = LocalDate.of(2019, 1, 1);
    private final LocalTime AT_10AM = LocalTime.of(10, 0);

    private final LocalDateTime START_01_JAN_2019_10AM = START_01_JAN_2019.atTime(AT_10AM);

    @Before
    public void setup() {
        unboundedDaily = ScheduleBuilder.newSchedule(EVENT_NAME_1).daily()
            .startingOn(START_01_JAN_2019, AT_10AM)
            .neverEnding();
    }

    @Test
    public void eventNameIsStoredInSchedule() {
        assertEquals(EVENT_NAME_1, unboundedDaily.getEventName());
    }

    @Test
    public void testStartDateOfUnboundedDailySchedule() {
        assertEquals(START_01_JAN_2019_10AM, unboundedDaily.startDate());
    }

    @Test
    public void getOccurrencesWithLimitUpto15ForUnboundedSchedule_Contain15Dates() {
        for (int occurrenceLimit = 1; occurrenceLimit <= 15; occurrenceLimit++) {
            checkOccurrences(unboundedDaily.getOccurrences(occurrenceLimit), unboundedDaily.startDate(), occurrenceLimit);
        }
    }

    @Test
    public void getOccurrencesFromDateOtherThanStartDate_ContainDatesOnlyAfterProvidedDate() {
        LocalDateTime _11_JAN_2019_10AM = START_01_JAN_2019_10AM.plusDays(10);
        List<LocalDateTime> occurrencesFrom = unboundedDaily.getOccurrencesFrom(_11_JAN_2019_10AM, 20);
        checkOccurrences(occurrencesFrom, _11_JAN_2019_10AM, 20);
    }

    @Test
    public void getOccurrencesFromDateBeforeStartDate_ReturnsOccurrencesFromStartDate() {
        LocalDateTime _31_DEC_2018_10AM = START_01_JAN_2019_10AM.minusDays(1);
        List<LocalDateTime> occurrencesFrom = unboundedDaily.getOccurrencesFrom(_31_DEC_2018_10AM, 15);
        checkOccurrences(occurrencesFrom, unboundedDaily.startDate(), 15);
    }

    @Test
    public void getOccurrencesWithZeroOrNegativeLimit_ReturnsZeroOccurrences() {
        List<LocalDateTime> occurrencesFrom = unboundedDaily.getOccurrencesFrom(START_01_JAN_2019_10AM, 0);
        assertTrue(occurrencesFrom.isEmpty());
    }

    @Test
    public void getOccurrencesWithNegativeLimit_ReturnsZeroOccurrences() {
        List<LocalDateTime> occurrencesFrom = unboundedDaily.getOccurrencesFrom(START_01_JAN_2019_10AM, -1);
        assertTrue(occurrencesFrom.isEmpty());
    }

    private void checkOccurrences(List<LocalDateTime> occurrencesToCheck, LocalDateTime startDate, int expectedNoOfOccurences) {
        assertEquals(expectedNoOfOccurences, occurrencesToCheck.size());
        for (int i = 0; i < expectedNoOfOccurences; i++) {
            assertEquals(startDate.plusDays(i), occurrencesToCheck.get(i));
        }
    }
}
