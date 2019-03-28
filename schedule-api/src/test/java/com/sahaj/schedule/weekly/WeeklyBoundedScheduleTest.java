package com.sahaj.schedule.weekly;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;
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

import com.sahaj.schedule.BoundedSchedule;
import com.sahaj.schedule.builder.ScheduleBuilder;

public class WeeklyBoundedScheduleTest {

    private BoundedSchedule weekly10AM_31Dec2018to24Jan2019_MonWedFri;

    private final String EVENT_NAME_1 = "Event name 1";
    private final String EVENT_NAME_2 = "Event name 2";

    private final LocalDate START_MONDAY_31_DEC_2018 = LocalDate.of(2018, 12, 31);
    private final LocalDate END_THURSDAY_24_JAN_2019 = LocalDate.of(2019, 1, 24);
    private final LocalTime AT_10AM = LocalTime.of(10, 0);
    private final LocalDateTime START_MONDAY_31_DEC_2018_10AM = START_MONDAY_31_DEC_2018.atTime(AT_10AM);

    private final LocalDateTime WEDNESDAY_02_JAN_2019_10AM = LocalDate.of(2019, 1, 2).atTime(AT_10AM);
    private final LocalDateTime FRIDAY_04_JAN_2019_10AM = LocalDate.of(2019, 1, 4).atTime(AT_10AM);
    private final LocalDateTime MONDAY_07_JAN_2019_10AM = LocalDate.of(2019, 1, 7).atTime(AT_10AM);
    private final LocalDateTime WEDNESDAY_09_JAN_2019_10AM = LocalDate.of(2019, 1, 9).atTime(AT_10AM);
    private final LocalDateTime FRIDAY_11_JAN_2019_10AM = LocalDate.of(2019, 1, 11).atTime(AT_10AM);

    private final LocalDateTime MONDAY_14_JAN_2019_10AM = LocalDate.of(2019, 1, 14).atTime(AT_10AM);
    private final LocalDateTime WEDNESDAY_16_JAN_2019_10AM = LocalDate.of(2019, 1, 16).atTime(AT_10AM);
    private final LocalDateTime FRIDAY_18_JAN_2019_10AM = LocalDate.of(2019, 1, 18).atTime(AT_10AM);
    private final LocalDateTime MONDAY_21_JAN_2019_10AM = LocalDate.of(2019, 1, 21).atTime(AT_10AM);
    private final LocalDateTime WEDNESDAY_23_JAN_2019_10AM = LocalDate.of(2019, 1, 23).atTime(AT_10AM);

    private BoundedSchedule weekly4PM_15Dec2018to10Jan2019_TuesThur;
    private final LocalDate START_15_DEC_2018 = LocalDate.of(2018, 12, 15);
    private final LocalDate END_THURSDAY_10_JAN_2019 = LocalDate.of(2019, 1, 10);
    private final LocalTime AT_4PM = LocalTime.of(16, 0);
    private final LocalDateTime START_SATURDAY_15_DEC_2018_4PM = START_15_DEC_2018.atTime(AT_4PM);
    private final LocalDateTime TUESDAY_18_DEC_2018_4PM = LocalDate.of(2018, 12, 18).atTime(AT_4PM);
    private final LocalDateTime THURSDAY_20_DEC_2018_4PM = LocalDate.of(2018, 12, 20).atTime(AT_4PM);
    private final LocalDateTime TUESDAY_25_DEC_2018_4PM = LocalDate.of(2018, 12, 25).atTime(AT_4PM);
    private final LocalDateTime THURSDAY_27_DEC_2018_4PM = LocalDate.of(2018, 12, 27).atTime(AT_4PM);
    private final LocalDateTime TUESDAY_1_JAN_2019_4PM = LocalDate.of(2019, 1, 1).atTime(AT_4PM);
    private final LocalDateTime THURSDAY_3_JAN_2019_4PM = LocalDate.of(2019, 1, 3).atTime(AT_4PM);
    private final LocalDateTime TUESDAY_8_JAN_2019_4PM = LocalDate.of(2019, 1, 8).atTime(AT_4PM);
    private final LocalDateTime THURSDAY_10_JAN_2019_4PM = LocalDate.of(2019, 1, 10).atTime(AT_4PM);

    @Before
    public void setup() {
        weekly10AM_31Dec2018to24Jan2019_MonWedFri = ScheduleBuilder.newSchedule(EVENT_NAME_1)
            .weekly(MONDAY, WEDNESDAY, FRIDAY)
            .startingOn(START_MONDAY_31_DEC_2018, AT_10AM)
            .endingOn(END_THURSDAY_24_JAN_2019);

        weekly4PM_15Dec2018to10Jan2019_TuesThur = ScheduleBuilder.newSchedule(EVENT_NAME_2)
            .weekly(TUESDAY, THURSDAY)
            .startingOn(START_15_DEC_2018, AT_4PM)
            .endingOn(END_THURSDAY_10_JAN_2019);
    }

    @Test
    public void eventNameIsStoredInSchedule() {
        assertEquals(EVENT_NAME_1, weekly10AM_31Dec2018to24Jan2019_MonWedFri.getEventName());
        assertEquals(EVENT_NAME_2, weekly4PM_15Dec2018to10Jan2019_TuesThur.getEventName());
    }

    @Test
    public void startDateIsAdjustedToFirstOccurrence() {
        assertEquals(START_MONDAY_31_DEC_2018_10AM, weekly10AM_31Dec2018to24Jan2019_MonWedFri.startDate());
        assertEquals(TUESDAY_18_DEC_2018_4PM, weekly4PM_15Dec2018to10Jan2019_TuesThur.startDate());
    }

    @Test
    public void getOccurrencesWithLimit_ContainsNumberofDatesSameAsProvidedLimitAndFromFirstOccurrence() {
        checkOccurrences(weekly10AM_31Dec2018to24Jan2019_MonWedFri.getOccurrences(5),
            Arrays.asList(
                START_MONDAY_31_DEC_2018_10AM,
                WEDNESDAY_02_JAN_2019_10AM,
                FRIDAY_04_JAN_2019_10AM,
                MONDAY_07_JAN_2019_10AM,
                WEDNESDAY_09_JAN_2019_10AM));

        checkOccurrences(weekly4PM_15Dec2018to10Jan2019_TuesThur.getOccurrences(5),
            Arrays.asList(
                TUESDAY_18_DEC_2018_4PM,
                THURSDAY_20_DEC_2018_4PM,
                TUESDAY_25_DEC_2018_4PM,
                THURSDAY_27_DEC_2018_4PM,
                TUESDAY_1_JAN_2019_4PM));
    }

    @Test
    public void getOccurrencesFromDateBeforeStartDate_ReturnsOccurrencesFromFirstOccurrence() {
        LocalDateTime _30_DEC_2018_10AM = START_MONDAY_31_DEC_2018_10AM.minusDays(1);
        List<LocalDateTime> occurrencesFrom = weekly10AM_31Dec2018to24Jan2019_MonWedFri.getOccurrencesFrom(_30_DEC_2018_10AM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(
                START_MONDAY_31_DEC_2018_10AM,
                WEDNESDAY_02_JAN_2019_10AM,
                FRIDAY_04_JAN_2019_10AM,
                MONDAY_07_JAN_2019_10AM,
                WEDNESDAY_09_JAN_2019_10AM));

        LocalDateTime _14_DEC_2018_4PM = START_SATURDAY_15_DEC_2018_4PM.minusDays(1);
        occurrencesFrom = weekly4PM_15Dec2018to10Jan2019_TuesThur.getOccurrencesFrom(_14_DEC_2018_4PM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(
                TUESDAY_18_DEC_2018_4PM,
                THURSDAY_20_DEC_2018_4PM,
                TUESDAY_25_DEC_2018_4PM,
                THURSDAY_27_DEC_2018_4PM,
                TUESDAY_1_JAN_2019_4PM));
    }

    @Test
    public void getOccurrencesFromDateSameAsFirstOccurrence_ContainDatesFromFirstOccurrence() {
        List<LocalDateTime> occurrencesFrom = weekly10AM_31Dec2018to24Jan2019_MonWedFri.getOccurrencesFrom(WEDNESDAY_02_JAN_2019_10AM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(
                WEDNESDAY_02_JAN_2019_10AM,
                FRIDAY_04_JAN_2019_10AM,
                MONDAY_07_JAN_2019_10AM,
                WEDNESDAY_09_JAN_2019_10AM,
                FRIDAY_11_JAN_2019_10AM));

        occurrencesFrom = weekly4PM_15Dec2018to10Jan2019_TuesThur.getOccurrencesFrom(TUESDAY_18_DEC_2018_4PM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(
                TUESDAY_18_DEC_2018_4PM,
                THURSDAY_20_DEC_2018_4PM,
                TUESDAY_25_DEC_2018_4PM,
                THURSDAY_27_DEC_2018_4PM,
                TUESDAY_1_JAN_2019_4PM));
    }

    @Test
    public void getOccurrencesFromDateAfterStartDate_ContainDatesOnlyAfterProvidedFromDate() {
        LocalDateTime THURSDAY_10_JAN_2019_10AM = START_MONDAY_31_DEC_2018_10AM.plusDays(10);
        List<LocalDateTime> occurrencesFrom = weekly10AM_31Dec2018to24Jan2019_MonWedFri.getOccurrencesFrom(THURSDAY_10_JAN_2019_10AM, 5);
        checkOccurrences(occurrencesFrom, Arrays.asList(
            FRIDAY_11_JAN_2019_10AM,
            MONDAY_14_JAN_2019_10AM,
            WEDNESDAY_16_JAN_2019_10AM,
            FRIDAY_18_JAN_2019_10AM,
            MONDAY_21_JAN_2019_10AM));

        LocalDateTime MONDAY_24_DEC_2018_4PM = START_SATURDAY_15_DEC_2018_4PM.plusDays(9);
        occurrencesFrom = weekly4PM_15Dec2018to10Jan2019_TuesThur.getOccurrencesFrom(MONDAY_24_DEC_2018_4PM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(
                TUESDAY_25_DEC_2018_4PM,
                THURSDAY_27_DEC_2018_4PM,
                TUESDAY_1_JAN_2019_4PM,
                THURSDAY_3_JAN_2019_4PM,
                TUESDAY_8_JAN_2019_4PM));
    }

    @Test
    public void getOccurrencesFromDateAfterStartDateWithDifferentTime_ContainDatesOnlyAfterProvidedFromDate() {
        LocalDateTime FRIDAY_11_JAN_2019_11AM = START_MONDAY_31_DEC_2018_10AM.plusDays(11).plusHours(1);
        List<LocalDateTime> occurrencesFrom = weekly10AM_31Dec2018to24Jan2019_MonWedFri.getOccurrencesFrom(FRIDAY_11_JAN_2019_11AM, 5);
        checkOccurrences(occurrencesFrom, Arrays.asList(
            MONDAY_14_JAN_2019_10AM,
            WEDNESDAY_16_JAN_2019_10AM,
            FRIDAY_18_JAN_2019_10AM,
            MONDAY_21_JAN_2019_10AM,
            WEDNESDAY_23_JAN_2019_10AM));

        LocalDateTime TUESDAY_25_DEC_2018_5PM = START_SATURDAY_15_DEC_2018_4PM.plusDays(10).plusHours(1);
        occurrencesFrom = weekly4PM_15Dec2018to10Jan2019_TuesThur.getOccurrencesFrom(TUESDAY_25_DEC_2018_5PM, 5);
        checkOccurrences(occurrencesFrom, Arrays.asList(
            THURSDAY_27_DEC_2018_4PM,
            TUESDAY_1_JAN_2019_4PM,
            THURSDAY_3_JAN_2019_4PM,
            TUESDAY_8_JAN_2019_4PM,
            THURSDAY_10_JAN_2019_4PM));
    }

    @Test
    public void getOccurrencesWithZeroOrNegativeLimit_ReturnsZeroOccurrences() {
        List<LocalDateTime> occurrencesFrom =
            weekly10AM_31Dec2018to24Jan2019_MonWedFri.getOccurrencesFrom(START_MONDAY_31_DEC_2018_10AM, 0);
        assertTrue(occurrencesFrom.isEmpty());

        occurrencesFrom = weekly4PM_15Dec2018to10Jan2019_TuesThur.getOccurrencesFrom(START_SATURDAY_15_DEC_2018_4PM, 0);
        assertTrue(occurrencesFrom.isEmpty());
    }

    @Test
    public void getOccurrencesWithNegativeLimit_ReturnsZeroOccurrences() {
        List<LocalDateTime> occurrencesFrom =
            weekly10AM_31Dec2018to24Jan2019_MonWedFri.getOccurrencesFrom(START_MONDAY_31_DEC_2018_10AM, -1);
        assertTrue(occurrencesFrom.isEmpty());

        occurrencesFrom = weekly4PM_15Dec2018to10Jan2019_TuesThur.getOccurrencesFrom(START_SATURDAY_15_DEC_2018_4PM, -1);
        assertTrue(occurrencesFrom.isEmpty());
    }

    @Test
    public void allOccurencesOfBoundedSchedule_ContainsAllDatesFromFirstTillLastOccurrcen() {
        List<LocalDateTime> allOccurrences = weekly10AM_31Dec2018to24Jan2019_MonWedFri.getAllOccurrences();
        checkOccurrences(allOccurrences, Arrays.asList(
            START_MONDAY_31_DEC_2018_10AM,
            WEDNESDAY_02_JAN_2019_10AM,
            FRIDAY_04_JAN_2019_10AM,
            MONDAY_07_JAN_2019_10AM,
            WEDNESDAY_09_JAN_2019_10AM,
            FRIDAY_11_JAN_2019_10AM,
            MONDAY_14_JAN_2019_10AM,
            WEDNESDAY_16_JAN_2019_10AM,
            FRIDAY_18_JAN_2019_10AM,
            MONDAY_21_JAN_2019_10AM,
            WEDNESDAY_23_JAN_2019_10AM));

        allOccurrences = weekly4PM_15Dec2018to10Jan2019_TuesThur.getAllOccurrences();
        checkOccurrences(allOccurrences, Arrays.asList(
            TUESDAY_18_DEC_2018_4PM,
            THURSDAY_20_DEC_2018_4PM,
            TUESDAY_25_DEC_2018_4PM,
            THURSDAY_27_DEC_2018_4PM,
            TUESDAY_1_JAN_2019_4PM,
            THURSDAY_3_JAN_2019_4PM,
            TUESDAY_8_JAN_2019_4PM,
            THURSDAY_10_JAN_2019_4PM));
    }

    @Test
    public void numberOfOccurencesOfBoundedSchedule_ShouldBeEqualToCountOfAllOccurrences() {
        assertEquals(11, weekly10AM_31Dec2018to24Jan2019_MonWedFri.getNumberOfOccurences());
        assertEquals(8, weekly4PM_15Dec2018to10Jan2019_TuesThur.getNumberOfOccurences());
    }

    private void checkOccurrences(List<LocalDateTime> occurrencesToCheck, List<LocalDateTime> expectedDates) {
        assertEquals(expectedDates.size(), occurrencesToCheck.size());
        for (int i = 0; i < expectedDates.size(); i++) {
            assertEquals(expectedDates.get(i), occurrencesToCheck.get(i));
        }
    }

}
