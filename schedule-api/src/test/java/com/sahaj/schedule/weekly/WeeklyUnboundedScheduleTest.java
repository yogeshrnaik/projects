package com.sahaj.schedule.weekly;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.WEDNESDAY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sahaj.schedule.Schedule;
import com.sahaj.schedule.builder.ScheduleBuilder;

public class WeeklyUnboundedScheduleTest {

    private Schedule weekly10AM_From01Jan2019_MonWedFri;

    private final String EVENT_NAME_1 = "Event name 1";

    private final LocalDate START_01_JAN_2019 = LocalDate.of(2019, 1, 1);
    private final LocalTime AT_10AM = LocalTime.of(10, 0);
    private final LocalDateTime START_01_JAN_2019_10AM = START_01_JAN_2019.atTime(AT_10AM);

    private final LocalDateTime _WEDNESDAY_02_JAN_2019_10AM = LocalDateTime.of(2019, 1, 2, 10, 0);
    private final LocalDateTime _FRIDAY_04_JAN_2019_10AM = LocalDateTime.of(2019, 1, 4, 10, 0);
    private final LocalDateTime _MONDAY_07_JAN_2019_10AM = LocalDateTime.of(2019, 1, 7, 10, 0);
    private final LocalDateTime _WEDNESDAY_09_JAN_2019_10AM = LocalDateTime.of(2019, 1, 9, 10, 0);
    private final LocalDateTime _FRIDAY_11_JAN_2019_10AM = LocalDateTime.of(2019, 1, 11, 10, 0);

    private final LocalDateTime _MONDAY_14_JAN_2019_10AM = LocalDateTime.of(2019, 1, 14, 10, 0);
    private final LocalDateTime _WEDNESDAY_16_JAN_2019_10AM = LocalDateTime.of(2019, 1, 16, 10, 0);
    private final LocalDateTime _FRIDAY_18_JAN_2019_10AM = LocalDateTime.of(2019, 1, 18, 10, 0);
    private final LocalDateTime _MONDAY_21_JAN_2019_10AM = LocalDateTime.of(2019, 1, 21, 10, 0);

    @Before
    public void setup() {
        weekly10AM_From01Jan2019_MonWedFri = ScheduleBuilder.newSchedule(EVENT_NAME_1)
            .weekly(MONDAY, WEDNESDAY, FRIDAY)
            .startingOn(START_01_JAN_2019, AT_10AM)
            .neverEnding();
    }

    @Test
    public void eventNameIsStoredInSchedule() {
        assertEquals(EVENT_NAME_1, weekly10AM_From01Jan2019_MonWedFri.getEventName());
    }

    @Test
    public void startDateIsAdjustedToFirstOccurrence() {
        assertEquals(_WEDNESDAY_02_JAN_2019_10AM, weekly10AM_From01Jan2019_MonWedFri.startDate());
    }

    @Test
    public void getOccurrencesWithLimit_ContainsNumberofDatesSameAsProvidedLimitAndFromFirstOccurrence() {
        checkOccurrences(weekly10AM_From01Jan2019_MonWedFri.getOccurrences(5),
            Arrays.asList(
                _WEDNESDAY_02_JAN_2019_10AM,
                _FRIDAY_04_JAN_2019_10AM,
                _MONDAY_07_JAN_2019_10AM,
                _WEDNESDAY_09_JAN_2019_10AM,
                _FRIDAY_11_JAN_2019_10AM));
    }

    @Test
    public void getOccurrencesFromDateBeforeStartDate_ReturnsOccurrencesFromFirstOccurrence() {
        LocalDateTime _31_DEC_2018_10AM = START_01_JAN_2019_10AM.minusDays(1);
        List<LocalDateTime> occurrencesFrom = weekly10AM_From01Jan2019_MonWedFri.getOccurrencesFrom(_31_DEC_2018_10AM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(
                _WEDNESDAY_02_JAN_2019_10AM,
                _FRIDAY_04_JAN_2019_10AM,
                _MONDAY_07_JAN_2019_10AM,
                _WEDNESDAY_09_JAN_2019_10AM,
                _FRIDAY_11_JAN_2019_10AM));
    }

    @Test
    public void getOccurrencesFromDateSameAsFirstOccurrence_ContainDatesFromFirstOccurrence() {
        List<LocalDateTime> occurrencesFrom = weekly10AM_From01Jan2019_MonWedFri.getOccurrencesFrom(_WEDNESDAY_02_JAN_2019_10AM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(
                _WEDNESDAY_02_JAN_2019_10AM,
                _FRIDAY_04_JAN_2019_10AM,
                _MONDAY_07_JAN_2019_10AM,
                _WEDNESDAY_09_JAN_2019_10AM,
                _FRIDAY_11_JAN_2019_10AM));
    }

    @Test
    public void getOccurrencesFromDateAfterStartDate_ContainDatesOnlyAfterProvidedFromDate() {
        LocalDateTime _10_JAN_2019_10AM = START_01_JAN_2019_10AM.plusDays(9);
        List<LocalDateTime> occurrencesFrom = weekly10AM_From01Jan2019_MonWedFri.getOccurrencesFrom(_10_JAN_2019_10AM, 5);
        checkOccurrences(occurrencesFrom, Arrays.asList(
            _FRIDAY_11_JAN_2019_10AM,
            _MONDAY_14_JAN_2019_10AM,
            _WEDNESDAY_16_JAN_2019_10AM,
            _FRIDAY_18_JAN_2019_10AM,
            _MONDAY_21_JAN_2019_10AM));
    }

    @Test
    public void getOccurrencesFromDateAfterStartDateWithDifferentTime_ContainDatesOnlyAfterProvidedFromDate() {
        LocalDateTime _11_JAN_2019_11AM = START_01_JAN_2019_10AM.plusDays(10).plusHours(1);
        List<LocalDateTime> occurrencesFrom = weekly10AM_From01Jan2019_MonWedFri.getOccurrencesFrom(_11_JAN_2019_11AM, 4);

        checkOccurrences(occurrencesFrom, Arrays.asList(
            _MONDAY_14_JAN_2019_10AM,
            _WEDNESDAY_16_JAN_2019_10AM,
            _FRIDAY_18_JAN_2019_10AM,
            _MONDAY_21_JAN_2019_10AM));
    }

    @Test
    public void getOccurrencesWithZeroOrNegativeLimit_ReturnsZeroOccurrences() {
        List<LocalDateTime> occurrencesFrom = weekly10AM_From01Jan2019_MonWedFri.getOccurrencesFrom(START_01_JAN_2019_10AM, 0);
        assertTrue(occurrencesFrom.isEmpty());
    }

    @Test
    public void getOccurrencesWithNegativeLimit_ReturnsZeroOccurrences() {
        List<LocalDateTime> occurrencesFrom = weekly10AM_From01Jan2019_MonWedFri.getOccurrencesFrom(START_01_JAN_2019_10AM, -1);
        assertTrue(occurrencesFrom.isEmpty());
    }

    private void checkOccurrences(List<LocalDateTime> occurrencesToCheck, List<LocalDateTime> expectedDates) {
        assertEquals(expectedDates.size(), occurrencesToCheck.size());
        for (int i = 0; i < expectedDates.size(); i++) {
            assertEquals(expectedDates.get(i), occurrencesToCheck.get(i));
        }
    }

}
