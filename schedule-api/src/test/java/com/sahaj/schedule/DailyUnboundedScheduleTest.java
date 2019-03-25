package com.sahaj.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sahaj.schedule.builder.ScheduleBuilder;

public class DailyUnboundedScheduleTest {

    private Schedule unboundedDaily;

    private final String EVENT_NAME_1 = "Event name 1";

    private final LocalDateTime START_01_JAN_2019 = LocalDateTime.of(2019, 1, 1, 10, 0);

    @Before
    public void setup() {
        unboundedDaily = ScheduleBuilder.newSchedule(EVENT_NAME_1).daily()
            .startingOn(START_01_JAN_2019)
            .neverEnding();
    }

    @Test
    public void eventNameIsStoredInSchedule() {
        assertEquals(EVENT_NAME_1, unboundedDaily.getEventName());
    }

    @Test
    public void testStartDateOfUnboundedDailySchedule() {
        assertEquals(START_01_JAN_2019, unboundedDaily.startDate());
    }

    @Test
    public void getOccurrencesWithLimitUpto15ForUnboundedSchedule_Contain15Dates() {
        for (int occurrenceLimit = 1; occurrenceLimit <= 15; occurrenceLimit++) {
            checkOccurrences(unboundedDaily.getOccurrences(occurrenceLimit), unboundedDaily.startDate(), occurrenceLimit);
        }
    }

    @Test
    public void getOccurrencesFromDateOtherThanStartDate_ContainDatesOnlyAfterProvidedDate() {
        LocalDateTime startDate = START_01_JAN_2019.plusDays(10);
        List<LocalDateTime> occurrencesFrom = unboundedDaily.getOccurrencesFrom(startDate, 20);
        checkOccurrences(occurrencesFrom, startDate, 20);
    }

    @Test
    public void getOccurrencesFromDateBeforeStartDate_ReturnsOccurrencesFromStartDate() {
        LocalDateTime _31_DEC_2018 = START_01_JAN_2019.minusDays(1);
        List<LocalDateTime> occurrencesFrom = unboundedDaily.getOccurrencesFrom(_31_DEC_2018, 15);
        checkOccurrences(occurrencesFrom, unboundedDaily.startDate(), 15);
    }

    @Test
    public void getOccurrencesWithZeroOrNegativeLimit_ReturnsZeroOccurrences() {
        List<LocalDateTime> occurrencesFrom = unboundedDaily.getOccurrencesFrom(START_01_JAN_2019, 0);
        assertTrue(occurrencesFrom.isEmpty());
    }

    @Test
    public void getOccurrencesWithNegativeLimit_ReturnsZeroOccurrences() {
        List<LocalDateTime> occurrencesFrom = unboundedDaily.getOccurrencesFrom(START_01_JAN_2019, -1);
        assertTrue(occurrencesFrom.isEmpty());
    }

    private void checkOccurrences(List<LocalDateTime> occurrencesToCheck, LocalDateTime startDate, int expectedNoOfOccurences) {
        assertEquals(expectedNoOfOccurences, occurrencesToCheck.size());
        for (int i = 0; i < expectedNoOfOccurences; i++) {
            assertEquals(startDate.plusDays(i), occurrencesToCheck.get(i));
        }
    }
}
