package com.sahaj.schedule.daily;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import com.sahaj.schedule.Schedule;
import com.sahaj.schedule.builder.ScheduleBuilder;

public class DailyUnboundedScheduleTest {

    private Schedule daily10AM_From01Jan2019;

    private final String EVENT_NAME_1 = "Event name 1";

    private final LocalDate START_01_JAN_2019 = LocalDate.of(2019, 1, 1);
    private final LocalTime AT_10AM = LocalTime.of(10, 0);
    private final LocalDateTime START_01_JAN_2019_10AM = LocalDateTime.of(2019, 1, 1, 10, 0);

    @Before
    public void setup() {
        daily10AM_From01Jan2019 = ScheduleBuilder.newSchedule(EVENT_NAME_1).daily()
            .startingOn(START_01_JAN_2019, AT_10AM)
            .neverEnding();
    }

    @Test
    public void eventNameIsStoredInSchedule() {
        assertEquals(EVENT_NAME_1, daily10AM_From01Jan2019.getEventName());
    }

    @Test
    public void startDateIsStoredInSchedule() {
        assertEquals(START_01_JAN_2019_10AM, daily10AM_From01Jan2019.startDate());
    }

    @Test
    public void getOccurrencesWithLimit_ContainsNumberofDatesSameAsProvidedLimitAndFromStartDateTime() {
        for (int occurrenceLimit = 1; occurrenceLimit <= 100; occurrenceLimit++) {
            checkOccurrences(daily10AM_From01Jan2019.getOccurrences(occurrenceLimit),
                getListOfLocalDateTimeFrom(START_01_JAN_2019_10AM, occurrenceLimit));
        }
    }

    @Test
    public void getOccurrencesFromDateBeforeStartDate_ReturnsOccurrencesFromStartDate() {
        LocalDateTime _31_DEC_2018_10AM = START_01_JAN_2019_10AM.minusDays(1);
        List<LocalDateTime> occurrencesFrom = daily10AM_From01Jan2019.getOccurrencesFrom(_31_DEC_2018_10AM, 15);
        checkOccurrences(occurrencesFrom, getListOfLocalDateTimeFrom(START_01_JAN_2019_10AM, 15));
    }

    @Test
    public void getOccurrencesFromDateSameAsStartDate_ContainDatesFromStartDate() {
        List<LocalDateTime> occurrencesFrom = daily10AM_From01Jan2019.getOccurrencesFrom(START_01_JAN_2019_10AM, 20);
        checkOccurrences(occurrencesFrom, getListOfLocalDateTimeFrom(START_01_JAN_2019_10AM, 20));
    }

    @Test
    public void getOccurrencesFromDateAfterStartDate_ContainDatesOnlyAfterProvidedFromDate() {
        LocalDateTime _11_JAN_2019_10AM = START_01_JAN_2019_10AM.plusDays(10);
        List<LocalDateTime> occurrencesFrom = daily10AM_From01Jan2019.getOccurrencesFrom(_11_JAN_2019_10AM, 20);
        checkOccurrences(occurrencesFrom, getListOfLocalDateTimeFrom(_11_JAN_2019_10AM, 20));
    }

    @Test
    public void getOccurrencesFromDateAfterStartDateWithDifferentTime_ContainDatesOnlyAfterProvidedFromDate() {
        LocalDateTime _11_JAN_2019_11AM = START_01_JAN_2019_10AM.plusDays(10).plusHours(1);
        List<LocalDateTime> occurrencesFrom = daily10AM_From01Jan2019.getOccurrencesFrom(_11_JAN_2019_11AM, 20);
        LocalDateTime _12_JAN_2019_10AM = START_01_JAN_2019_10AM.plusDays(11);
        checkOccurrences(occurrencesFrom, getListOfLocalDateTimeFrom(_12_JAN_2019_10AM, 20));
    }

    @Test
    public void getOccurrencesWithZeroOrNegativeLimit_ReturnsZeroOccurrences() {
        List<LocalDateTime> occurrencesFrom = daily10AM_From01Jan2019.getOccurrencesFrom(START_01_JAN_2019_10AM, 0);
        assertTrue(occurrencesFrom.isEmpty());
    }

    @Test
    public void getOccurrencesWithNegativeLimit_ReturnsZeroOccurrences() {
        List<LocalDateTime> occurrencesFrom = daily10AM_From01Jan2019.getOccurrencesFrom(START_01_JAN_2019_10AM, -1);
        assertTrue(occurrencesFrom.isEmpty());
    }

    private List<LocalDateTime> getListOfLocalDateTimeFrom(LocalDateTime first, int noOfDates) {
        return IntStream.range(0, noOfDates)
            .mapToObj(i -> first.plusDays(i))
            .collect(Collectors.toList());
    }

    private void checkOccurrences(List<LocalDateTime> occurrencesToCheck, List<LocalDateTime> expectedDates) {
        assertEquals(expectedDates.size(), occurrencesToCheck.size());
        for (int i = 0; i < expectedDates.size(); i++) {
            assertEquals(expectedDates.get(i), occurrencesToCheck.get(i));
        }
    }
}
