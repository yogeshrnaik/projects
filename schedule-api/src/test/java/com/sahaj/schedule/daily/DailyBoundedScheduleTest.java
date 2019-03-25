package com.sahaj.schedule.daily;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sahaj.schedule.BoundedSchedule;
import com.sahaj.schedule.builder.ScheduleBuilder;

public class DailyBoundedScheduleTest {

    private BoundedSchedule boundedDaily;

    private final String EVENT_NAME_1 = "Event name 1";

    private final LocalDateTime START_01_JAN_2019 = LocalDateTime.of(2019, 1, 1, 10, 0);
    private final LocalDateTime END_15_JAN_2019 = LocalDateTime.of(2019, 1, 15, 10, 0);

    @Before
    public void setup() {
        boundedDaily = ScheduleBuilder.newSchedule(EVENT_NAME_1).daily()
            .startingOn(START_01_JAN_2019)
            .endingOn(END_15_JAN_2019);
    }

    @Test
    public void eventNameIsStoredInSchedule() {
        assertEquals(EVENT_NAME_1, boundedDaily.getEventName());
    }

    @Test
    public void testStartAndEndDatesOfBoundedDailySchedule() {
        assertEquals(START_01_JAN_2019, boundedDaily.startDate());
        assertEquals(END_15_JAN_2019, boundedDaily.endDate());
    }

    @Test
    public void getOccurrencesWithLimitUpto15ForScheduleOf15Days_ContainDatesEqualtoLimitProvided() {
        for (int occurrenceLimit = 1; occurrenceLimit <= 15; occurrenceLimit++) {
            checkOccurrences(boundedDaily.getOccurrences(occurrenceLimit), boundedDaily.startDate(), occurrenceLimit);
        }
    }

    @Test
    public void getOccurrencesWithLimitMoreThan15ForScheduleOf15Days_Contain15DatesOnly() {
        checkOccurrences(boundedDaily.getOccurrences(16), boundedDaily.startDate(), 15);
        checkOccurrences(boundedDaily.getOccurrences(20), boundedDaily.startDate(), 15);
    }

    @Test
    public void getOccurrencesFromDateOtherThanStartDate_ContainDatesOnlyAfterProvidedDate() {
        LocalDateTime startDate = START_01_JAN_2019.plusDays(10);
        List<LocalDateTime> occurrencesFrom = boundedDaily.getOccurrencesFrom(startDate, 5);
        checkOccurrences(occurrencesFrom, startDate, 5);
    }

    @Test
    public void getOccurrencesFromDateOtherThanStartWithMoreLimit_ContainDatesOnlyAfterProvidedDateAndTillEndDate() {
        LocalDateTime _11_JAN_2019 = START_01_JAN_2019.plusDays(10);
        List<LocalDateTime> occurrencesFrom = boundedDaily.getOccurrencesFrom(_11_JAN_2019, 20);

        checkOccurrences(occurrencesFrom, _11_JAN_2019, 5);
        assertTrue(occurrencesFrom.contains(boundedDaily.endDate()));
        assertFalse(occurrencesFrom.contains(boundedDaily.endDate().plusDays(1)));
    }

    @Test
    public void getOccurrencesFromEndDate_ContainOnlyEndDate() {
        List<LocalDateTime> occurrencesFrom = boundedDaily.getOccurrencesFrom(END_15_JAN_2019, 20);
        checkOccurrences(occurrencesFrom, END_15_JAN_2019, 1);
        assertTrue(occurrencesFrom.contains(boundedDaily.endDate()));
    }

    @Test
    public void getOccurrencesFromDateBeforeStartDate_ReturnsOccurrencesFromStartDate() {
        LocalDateTime _31_DEC_2018 = START_01_JAN_2019.minusDays(1);
        List<LocalDateTime> occurrencesFrom = boundedDaily.getOccurrencesFrom(_31_DEC_2018, 5);
        checkOccurrences(occurrencesFrom, boundedDaily.startDate(), 5);
    }

    @Test
    public void getOccurrencesFromDateAfterEndStart_ReturnsZeroOccurrences() {
        LocalDateTime _16_JAN_2019 = END_15_JAN_2019.plusDays(1);
        List<LocalDateTime> occurrencesFrom = boundedDaily.getOccurrencesFrom(_16_JAN_2019, 20);
        assertTrue(occurrencesFrom.isEmpty());
    }

    @Test
    public void getOccurrencesWithZeroOrNegativeLimit_ReturnsZeroOccurrences() {
        List<LocalDateTime> occurrencesFrom = boundedDaily.getOccurrencesFrom(START_01_JAN_2019, 0);
        assertTrue(occurrencesFrom.isEmpty());
    }

    @Test
    public void getOccurrencesWithNegativeLimit_ReturnsZeroOccurrences() {
        List<LocalDateTime> occurrencesFrom = boundedDaily.getOccurrencesFrom(START_01_JAN_2019, -1);
        assertTrue(occurrencesFrom.isEmpty());
    }

    @Test
    public void allOccurencesOfBoundedScheduleOf15Days_Contain15Dates() {
        checkOccurrences(boundedDaily.getAllOccurrences(), boundedDaily.startDate(), 15);
    }

    private void checkOccurrences(List<LocalDateTime> occurrencesToCheck, LocalDateTime startDate, int expectedNoOfOccurences) {
        assertEquals(expectedNoOfOccurences, occurrencesToCheck.size());
        for (int i = 0; i < expectedNoOfOccurences; i++) {
            assertEquals(startDate.plusDays(i), occurrencesToCheck.get(i));
        }
    }

    @Test
    public void numberOfOccurencesOfBoundedScheduleOf15Days_ShouldBe15() {
        assertEquals(15, boundedDaily.getNumberOfOccurences());
    }

}
