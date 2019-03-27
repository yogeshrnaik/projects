package com.sahaj.schedule.daily;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sahaj.schedule.BoundedSchedule;
import com.sahaj.schedule.builder.ScheduleBuilder;

public class DailyBoundedScheduleTest {

    private BoundedSchedule daily8PM_From01Jan_to_15Jan2019;

    private final String EVENT_NAME_1 = "Event name 1";

    private final LocalDate START_01_JAN_2019 = LocalDate.of(2019, 1, 1);
    private final LocalDate END_15_JAN_2019 = LocalDate.of(2019, 1, 15);
    private final LocalTime AT_8PM = LocalTime.of(20, 0);

    private final LocalDateTime START_01_JAN_2019_8PM = START_01_JAN_2019.atTime(AT_8PM);
    private final LocalDateTime END_15_JAN_2019_8PM = END_15_JAN_2019.atTime(AT_8PM);

    @Before
    public void setup() {
        daily8PM_From01Jan_to_15Jan2019 = ScheduleBuilder.newSchedule(EVENT_NAME_1).daily()
            .startingOn(START_01_JAN_2019, AT_8PM)
            .endingOn(END_15_JAN_2019);
    }

    @Test
    public void eventNameIsStoredInSchedule() {
        assertEquals(EVENT_NAME_1, daily8PM_From01Jan_to_15Jan2019.getEventName());
    }

    @Test
    public void startAndEndDatesAreStoredInSchedule() {
        assertEquals(START_01_JAN_2019_8PM, daily8PM_From01Jan_to_15Jan2019.startDate());
        assertEquals(END_15_JAN_2019_8PM, daily8PM_From01Jan_to_15Jan2019.endDate());
    }

    @Test
    public void getOccurrencesWithLimitUpto15ForScheduleOf15Days_ContainDatesEqualtoLimitProvided() {
        for (int occurrenceLimit = 1; occurrenceLimit <= 15; occurrenceLimit++) {
            checkOccurrences(daily8PM_From01Jan_to_15Jan2019.getOccurrences(occurrenceLimit), START_01_JAN_2019_8PM, occurrenceLimit);
        }
    }

    @Test
    public void getOccurrencesWithLimitMoreThan15ForScheduleOf15Days_Contain15DatesOnly() {
        checkOccurrences(daily8PM_From01Jan_to_15Jan2019.getOccurrences(16), START_01_JAN_2019_8PM, 15);
        checkOccurrences(daily8PM_From01Jan_to_15Jan2019.getOccurrences(Integer.MAX_VALUE), START_01_JAN_2019_8PM, 15);
    }

    @Test
    public void getOccurrencesFromDateBeforeStartDate_ReturnsOccurrencesFromStartDate() {
        LocalDateTime _31_DEC_2018_8PM = START_01_JAN_2019_8PM.minusDays(1);
        List<LocalDateTime> occurrencesFrom = daily8PM_From01Jan_to_15Jan2019.getOccurrencesFrom(_31_DEC_2018_8PM, 5);
        checkOccurrences(occurrencesFrom, START_01_JAN_2019_8PM, 5);
    }

    @Test
    public void getOccurrencesFromDateSameAsStartDate_ContainDatesFromStartDate() {
        List<LocalDateTime> occurrencesFrom = daily8PM_From01Jan_to_15Jan2019.getOccurrencesFrom(START_01_JAN_2019_8PM, 5);
        checkOccurrences(occurrencesFrom, START_01_JAN_2019_8PM, 5);
    }

    @Test
    public void getOccurrencesFromDateAfterStartDate_ContainDatesOnlyAfterProvidedFromDate() {
        LocalDateTime _11_JAN_2019_8PM = START_01_JAN_2019_8PM.plusDays(10);
        List<LocalDateTime> occurrencesFrom = daily8PM_From01Jan_to_15Jan2019.getOccurrencesFrom(_11_JAN_2019_8PM, 5);
        checkOccurrences(occurrencesFrom, _11_JAN_2019_8PM, 5);
    }

    @Test
    public void getOccurrencesFromDateAfterStartDateWithMoreLimit_ContainDatesAfterProvidedFromDateAndTillEndDate() {
        LocalDateTime _11_JAN_2019_8PM = START_01_JAN_2019_8PM.plusDays(10);
        List<LocalDateTime> occurrencesFrom = daily8PM_From01Jan_to_15Jan2019.getOccurrencesFrom(_11_JAN_2019_8PM, 20);

        checkOccurrences(occurrencesFrom, _11_JAN_2019_8PM, 5);
        lastOccurrenceIsOfEndDate(occurrencesFrom);
    }

    private void lastOccurrenceIsOfEndDate(List<LocalDateTime> occurrencesFrom) {
        assertEquals(occurrencesFrom.get(occurrencesFrom.size() - 1), daily8PM_From01Jan_to_15Jan2019.endDate());
        assertFalse(occurrencesFrom.contains(daily8PM_From01Jan_to_15Jan2019.endDate().plusDays(1)));
    }

    @Test
    public void getOccurrencesFromDateOtherThanStartWithDifferentTimeWithMoreLimit_ContainDatesAfterProvidedFromDateAndTillEndDate() {
        LocalDateTime _11_JAN_2019_9PM = START_01_JAN_2019_8PM.plusDays(10).plusHours(1);
        List<LocalDateTime> occurrencesFrom = daily8PM_From01Jan_to_15Jan2019.getOccurrencesFrom(_11_JAN_2019_9PM, 20);

        LocalDateTime _12_JAN_2019_8PM = START_01_JAN_2019_8PM.plusDays(11);
        checkOccurrences(occurrencesFrom, _12_JAN_2019_8PM, 4);
        lastOccurrenceIsOfEndDate(occurrencesFrom);
    }

    @Test
    public void getOccurrencesFromOneDayBeforeEndDateWithDifferentTimeWithMoreLimit_ContainsOnlyEndDate() {
        LocalDateTime _14_JAN_2019_9PM = START_01_JAN_2019_8PM.plusDays(13).plusHours(1);
        List<LocalDateTime> occurrencesFrom = daily8PM_From01Jan_to_15Jan2019.getOccurrencesFrom(_14_JAN_2019_9PM, 20);

        checkOccurrences(occurrencesFrom, END_15_JAN_2019_8PM, 1);
        lastOccurrenceIsOfEndDate(occurrencesFrom);
    }

    @Test
    public void getOccurrencesFromEndDate_ContainOnlyEndDate() {
        List<LocalDateTime> occurrencesFrom = daily8PM_From01Jan_to_15Jan2019.getOccurrencesFrom(END_15_JAN_2019_8PM, 20);
        checkOccurrences(occurrencesFrom, END_15_JAN_2019_8PM, 1);
        assertTrue(occurrencesFrom.contains(daily8PM_From01Jan_to_15Jan2019.endDate()));
    }

    @Test
    public void getOccurrencesFromDateAfterEndStart_ReturnsZeroOccurrences() {
        LocalDateTime _15_JAN_2019_9PM = END_15_JAN_2019_8PM.plusHours(1);
        List<LocalDateTime> occurrencesFrom = daily8PM_From01Jan_to_15Jan2019.getOccurrencesFrom(_15_JAN_2019_9PM, 20);
        assertTrue(occurrencesFrom.isEmpty());
    }

    @Test
    public void getOccurrencesWithZeroOrNegativeLimit_ReturnsZeroOccurrences() {
        List<LocalDateTime> occurrencesFrom = daily8PM_From01Jan_to_15Jan2019.getOccurrencesFrom(START_01_JAN_2019_8PM, 0);
        assertTrue(occurrencesFrom.isEmpty());
    }

    @Test
    public void getOccurrencesWithNegativeLimit_ReturnsZeroOccurrences() {
        List<LocalDateTime> occurrencesFrom = daily8PM_From01Jan_to_15Jan2019.getOccurrencesFrom(START_01_JAN_2019_8PM, -1);
        assertTrue(occurrencesFrom.isEmpty());
    }

    @Test
    public void allOccurencesOfBoundedScheduleOf15Days_Contain15Dates() {
        List<LocalDateTime> allOccurrences = daily8PM_From01Jan_to_15Jan2019.getAllOccurrences();
        checkOccurrences(allOccurrences, START_01_JAN_2019_8PM, 15);
        lastOccurrenceIsOfEndDate(allOccurrences);
    }

    private void checkOccurrences(List<LocalDateTime> occurrencesToCheck, LocalDateTime startDate, int expectedNoOfOccurences) {
        assertEquals(expectedNoOfOccurences, occurrencesToCheck.size());
        for (int i = 0; i < expectedNoOfOccurences; i++) {
            assertEquals(startDate.plusDays(i), occurrencesToCheck.get(i));
        }
    }

    @Test
    public void numberOfOccurencesOfBoundedScheduleOf15Days_ShouldBe15() {
        assertEquals(15, daily8PM_From01Jan_to_15Jan2019.getNumberOfOccurences());
    }

}
