package com.sahaj.schedule.biweekly;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
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

public class BiWeeklyBoundedScheduleTest {

    private BoundedSchedule biweekly10AM_31Dec2018to24Jan2019_MonWedFri;

    private final String EVENT_NAME_1 = "Event name 1";
    private final String EVENT_NAME_2 = "Event name 2";
    private final String EVENT_NAME_3 = "Event name 3";

    private final LocalDate START_MONDAY_31_DEC_2018 = LocalDate.of(2018, 12, 31);
    private final LocalDate END_THURSDAY_24_JAN_2019 = LocalDate.of(2019, 1, 24);
    private final LocalTime AT_10AM = LocalTime.of(10, 0);
    private final LocalDateTime START_MONDAY_31_DEC_2018_10AM = START_MONDAY_31_DEC_2018.atTime(AT_10AM);
    private final LocalDateTime WEDNESDAY_02_JAN_2019_10AM = LocalDate.of(2019, 1, 2).atTime(AT_10AM);
    private final LocalDateTime FRIDAY_04_JAN_2019_10AM = LocalDate.of(2019, 1, 4).atTime(AT_10AM);
    private final LocalDateTime MONDAY_14_JAN_2019_10AM = LocalDate.of(2019, 1, 14).atTime(AT_10AM);
    private final LocalDateTime WEDNESDAY_16_JAN_2019_10AM = LocalDate.of(2019, 1, 16).atTime(AT_10AM);
    private final LocalDateTime FRIDAY_18_JAN_2019_10AM = LocalDate.of(2019, 1, 18).atTime(AT_10AM);

    private BoundedSchedule biweekly4PM_15Dec2018to10Jan2019_TuesThur;
    private final LocalDate START_15_DEC_2018 = LocalDate.of(2018, 12, 15);
    private final LocalDate END_THURSDAY_10_JAN_2019 = LocalDate.of(2019, 1, 10);
    private final LocalTime AT_4PM = LocalTime.of(16, 0);
    private final LocalDateTime START_SATURDAY_15_DEC_2018_4PM = START_15_DEC_2018.atTime(AT_4PM);
    private final LocalDateTime TUESDAY_18_DEC_2018_4PM = LocalDate.of(2018, 12, 18).atTime(AT_4PM);
    private final LocalDateTime THURSDAY_20_DEC_2018_4PM = LocalDate.of(2018, 12, 20).atTime(AT_4PM);
    private final LocalDateTime TUESDAY_1_JAN_2019_4PM = LocalDate.of(2019, 1, 1).atTime(AT_4PM);
    private final LocalDateTime THURSDAY_3_JAN_2019_4PM = LocalDate.of(2019, 1, 3).atTime(AT_4PM);

    private BoundedSchedule biweekly9PM_From22Dec2018to03Feb2019_SatSun;
    private final LocalDate START_22_DEC_2018 = LocalDate.of(2018, 12, 22);
    private final LocalDate END_SUNDAY_03_FEB_2019 = LocalDate.of(2019, 2, 3);
    private final LocalTime AT_9PM = LocalTime.of(21, 0);
    private final LocalDateTime START_SATURDAY_22_DEC_2018_9PM = START_22_DEC_2018.atTime(AT_9PM);
    private final LocalDateTime SUNDAY_30_DEC_2018_9PM = LocalDate.of(2018, 12, 30).atTime(AT_9PM);
    private final LocalDateTime SATURDAY_05_JAN_2019_9PM = LocalDate.of(2019, 1, 5).atTime(AT_9PM);
    private final LocalDateTime SUNDAY_13_JAN_2019_9PM = LocalDate.of(2019, 1, 13).atTime(AT_9PM);
    private final LocalDateTime SATURDAY_19_JAN_2019_9PM = LocalDate.of(2019, 1, 19).atTime(AT_9PM);
    private final LocalDateTime SUNDAY_27_JAN_2019_9PM = LocalDate.of(2019, 1, 27).atTime(AT_9PM);
    private final LocalDateTime SATURDAY_02_FEB_2019_9PM = LocalDate.of(2019, 2, 2).atTime(AT_9PM);

    @Before
    public void setup() {
        biweekly10AM_31Dec2018to24Jan2019_MonWedFri = ScheduleBuilder.newSchedule(EVENT_NAME_1)
            .biWeekly(MONDAY, WEDNESDAY, FRIDAY)
            .startingOn(START_MONDAY_31_DEC_2018, AT_10AM)
            .endingOn(END_THURSDAY_24_JAN_2019);

        biweekly4PM_15Dec2018to10Jan2019_TuesThur = ScheduleBuilder.newSchedule(EVENT_NAME_2)
            .biWeekly(TUESDAY, THURSDAY)
            .startingOn(START_15_DEC_2018, AT_4PM)
            .endingOn(END_THURSDAY_10_JAN_2019);

        biweekly9PM_From22Dec2018to03Feb2019_SatSun = ScheduleBuilder.newSchedule(EVENT_NAME_3)
            .biWeekly(SATURDAY, SUNDAY)
            .startingOn(START_22_DEC_2018, AT_9PM)
            .endingOn(END_SUNDAY_03_FEB_2019);
    }

    @Test
    public void eventNameIsStoredInSchedule() {
        assertEquals(EVENT_NAME_1, biweekly10AM_31Dec2018to24Jan2019_MonWedFri.getEventName());
        assertEquals(EVENT_NAME_2, biweekly4PM_15Dec2018to10Jan2019_TuesThur.getEventName());
        assertEquals(EVENT_NAME_3, biweekly9PM_From22Dec2018to03Feb2019_SatSun.getEventName());
    }

    @Test
    public void startDateIsAdjustedToFirstOccurrence() {
        assertEquals(START_MONDAY_31_DEC_2018_10AM, biweekly10AM_31Dec2018to24Jan2019_MonWedFri.startDate());
        assertEquals(TUESDAY_18_DEC_2018_4PM, biweekly4PM_15Dec2018to10Jan2019_TuesThur.startDate());
        assertEquals(START_SATURDAY_22_DEC_2018_9PM, biweekly9PM_From22Dec2018to03Feb2019_SatSun.startDate());
    }

    @Test
    public void getOccurrencesWithLimit_ContainsNumberofDatesSameAsProvidedLimitAndFromFirstOccurrence() {
        checkOccurrences(biweekly10AM_31Dec2018to24Jan2019_MonWedFri.getOccurrences(5),
            Arrays.asList(
                START_MONDAY_31_DEC_2018_10AM,
                WEDNESDAY_02_JAN_2019_10AM,
                FRIDAY_04_JAN_2019_10AM,
                MONDAY_14_JAN_2019_10AM,
                WEDNESDAY_16_JAN_2019_10AM));

        checkOccurrences(biweekly4PM_15Dec2018to10Jan2019_TuesThur.getOccurrences(5),
            Arrays.asList(
                TUESDAY_18_DEC_2018_4PM,
                THURSDAY_20_DEC_2018_4PM,
                TUESDAY_1_JAN_2019_4PM,
                THURSDAY_3_JAN_2019_4PM));

        checkOccurrences(biweekly9PM_From22Dec2018to03Feb2019_SatSun.getOccurrences(5),
            Arrays.asList(START_SATURDAY_22_DEC_2018_9PM,
                SUNDAY_30_DEC_2018_9PM,
                SATURDAY_05_JAN_2019_9PM,
                SUNDAY_13_JAN_2019_9PM,
                SATURDAY_19_JAN_2019_9PM));
    }

    @Test
    public void getOccurrencesFromDateBeforeStartDate_ReturnsOccurrencesFromFirstOccurrence() {
        LocalDateTime _30_DEC_2018_10AM = START_MONDAY_31_DEC_2018_10AM.minusDays(1);
        List<LocalDateTime> occurrencesFrom = biweekly10AM_31Dec2018to24Jan2019_MonWedFri.getOccurrencesFrom(_30_DEC_2018_10AM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(
                START_MONDAY_31_DEC_2018_10AM,
                WEDNESDAY_02_JAN_2019_10AM,
                FRIDAY_04_JAN_2019_10AM,
                MONDAY_14_JAN_2019_10AM,
                WEDNESDAY_16_JAN_2019_10AM));

        LocalDateTime _14_DEC_2018_4PM = START_SATURDAY_15_DEC_2018_4PM.minusDays(1);
        occurrencesFrom = biweekly4PM_15Dec2018to10Jan2019_TuesThur.getOccurrencesFrom(_14_DEC_2018_4PM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(
                TUESDAY_18_DEC_2018_4PM,
                THURSDAY_20_DEC_2018_4PM,
                TUESDAY_1_JAN_2019_4PM,
                THURSDAY_3_JAN_2019_4PM));

        LocalDateTime _21_DEC_2018_9PM = START_SATURDAY_22_DEC_2018_9PM.minusDays(1);
        occurrencesFrom = biweekly9PM_From22Dec2018to03Feb2019_SatSun.getOccurrencesFrom(_21_DEC_2018_9PM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(START_SATURDAY_22_DEC_2018_9PM,
                SUNDAY_30_DEC_2018_9PM,
                SATURDAY_05_JAN_2019_9PM,
                SUNDAY_13_JAN_2019_9PM,
                SATURDAY_19_JAN_2019_9PM));
    }

    @Test
    public void getOccurrencesFromDateSameAsFirstOccurrence_ContainDatesFromFirstOccurrence() {
        List<LocalDateTime> occurrencesFrom = biweekly10AM_31Dec2018to24Jan2019_MonWedFri.getOccurrencesFrom(WEDNESDAY_02_JAN_2019_10AM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(
                WEDNESDAY_02_JAN_2019_10AM,
                FRIDAY_04_JAN_2019_10AM,
                MONDAY_14_JAN_2019_10AM,
                WEDNESDAY_16_JAN_2019_10AM,
                FRIDAY_18_JAN_2019_10AM));

        occurrencesFrom = biweekly4PM_15Dec2018to10Jan2019_TuesThur.getOccurrencesFrom(TUESDAY_18_DEC_2018_4PM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(
                TUESDAY_18_DEC_2018_4PM,
                THURSDAY_20_DEC_2018_4PM,
                TUESDAY_1_JAN_2019_4PM,
                THURSDAY_3_JAN_2019_4PM));

        occurrencesFrom = biweekly9PM_From22Dec2018to03Feb2019_SatSun.getOccurrencesFrom(START_SATURDAY_22_DEC_2018_9PM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(
                START_SATURDAY_22_DEC_2018_9PM,
                SUNDAY_30_DEC_2018_9PM,
                SATURDAY_05_JAN_2019_9PM,
                SUNDAY_13_JAN_2019_9PM,
                SATURDAY_19_JAN_2019_9PM));
    }

    @Test
    public void getOccurrencesFromDateAfterStartDate_ContainDatesOnlyAfterProvidedFromDate() {
        LocalDateTime THURSDAY_10_JAN_2019_10AM = START_MONDAY_31_DEC_2018_10AM.plusDays(10);
        List<LocalDateTime> occurrencesFrom = biweekly10AM_31Dec2018to24Jan2019_MonWedFri.getOccurrencesFrom(THURSDAY_10_JAN_2019_10AM, 5);
        checkOccurrences(occurrencesFrom, Arrays.asList(
            MONDAY_14_JAN_2019_10AM,
            WEDNESDAY_16_JAN_2019_10AM,
            FRIDAY_18_JAN_2019_10AM));

        LocalDateTime MONDAY_24_DEC_2018_4PM = START_SATURDAY_15_DEC_2018_4PM.plusDays(9);
        occurrencesFrom = biweekly4PM_15Dec2018to10Jan2019_TuesThur.getOccurrencesFrom(MONDAY_24_DEC_2018_4PM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(
                TUESDAY_1_JAN_2019_4PM,
                THURSDAY_3_JAN_2019_4PM));

        LocalDateTime MONDAY_31_DEC_2018_9PM = START_SATURDAY_22_DEC_2018_9PM.plusDays(9);
        occurrencesFrom = biweekly9PM_From22Dec2018to03Feb2019_SatSun.getOccurrencesFrom(MONDAY_31_DEC_2018_9PM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(
                SATURDAY_05_JAN_2019_9PM,
                SUNDAY_13_JAN_2019_9PM,
                SATURDAY_19_JAN_2019_9PM,
                SUNDAY_27_JAN_2019_9PM,
                SATURDAY_02_FEB_2019_9PM));
    }

    @Test
    public void getOccurrencesFromDateAfterStartDateWithDifferentTime_ContainDatesOnlyAfterProvidedFromDate() {
        LocalDateTime FRIDAY_11_JAN_2019_11AM = START_MONDAY_31_DEC_2018_10AM.plusDays(11).plusHours(1);
        List<LocalDateTime> occurrencesFrom = biweekly10AM_31Dec2018to24Jan2019_MonWedFri.getOccurrencesFrom(FRIDAY_11_JAN_2019_11AM, 5);
        checkOccurrences(occurrencesFrom, Arrays.asList(
            MONDAY_14_JAN_2019_10AM,
            WEDNESDAY_16_JAN_2019_10AM,
            FRIDAY_18_JAN_2019_10AM));

        LocalDateTime TUESDAY_25_DEC_2018_5PM = START_SATURDAY_15_DEC_2018_4PM.plusDays(10).plusHours(1);
        occurrencesFrom = biweekly4PM_15Dec2018to10Jan2019_TuesThur.getOccurrencesFrom(TUESDAY_25_DEC_2018_5PM, 5);
        checkOccurrences(occurrencesFrom, Arrays.asList(
            TUESDAY_1_JAN_2019_4PM,
            THURSDAY_3_JAN_2019_4PM));

        LocalDateTime SUNDAY_30_DEC_2018_10PM = START_SATURDAY_22_DEC_2018_9PM.plusDays(8).plusHours(1);
        occurrencesFrom = biweekly9PM_From22Dec2018to03Feb2019_SatSun.getOccurrencesFrom(SUNDAY_30_DEC_2018_10PM, 5);
        checkOccurrences(occurrencesFrom, Arrays.asList(
            SATURDAY_05_JAN_2019_9PM,
            SUNDAY_13_JAN_2019_9PM,
            SATURDAY_19_JAN_2019_9PM,
            SUNDAY_27_JAN_2019_9PM,
            SATURDAY_02_FEB_2019_9PM));
    }

    @Test
    public void getOccurrencesWithZeroOrNegativeLimit_ReturnsZeroOccurrences() {
        List<LocalDateTime> occurrencesFrom =
            biweekly10AM_31Dec2018to24Jan2019_MonWedFri.getOccurrencesFrom(START_MONDAY_31_DEC_2018_10AM, 0);
        assertTrue(occurrencesFrom.isEmpty());

        occurrencesFrom = biweekly4PM_15Dec2018to10Jan2019_TuesThur.getOccurrencesFrom(START_SATURDAY_15_DEC_2018_4PM, 0);
        assertTrue(occurrencesFrom.isEmpty());

        occurrencesFrom = biweekly9PM_From22Dec2018to03Feb2019_SatSun.getOccurrencesFrom(START_SATURDAY_22_DEC_2018_9PM, 0);
        assertTrue(occurrencesFrom.isEmpty());
    }

    @Test
    public void getOccurrencesWithNegativeLimit_ReturnsZeroOccurrences() {
        List<LocalDateTime> occurrencesFrom =
            biweekly10AM_31Dec2018to24Jan2019_MonWedFri.getOccurrencesFrom(START_MONDAY_31_DEC_2018_10AM, -1);
        assertTrue(occurrencesFrom.isEmpty());

        occurrencesFrom = biweekly4PM_15Dec2018to10Jan2019_TuesThur.getOccurrencesFrom(START_SATURDAY_15_DEC_2018_4PM, -1);
        assertTrue(occurrencesFrom.isEmpty());

        occurrencesFrom = biweekly9PM_From22Dec2018to03Feb2019_SatSun.getOccurrencesFrom(START_SATURDAY_22_DEC_2018_9PM, -1);
        assertTrue(occurrencesFrom.isEmpty());
    }

    @Test
    public void allOccurencesOfBoundedSchedule_ContainsAllDatesFromFirstTillLastOccurrence() {
        List<LocalDateTime> allOccurrences = biweekly10AM_31Dec2018to24Jan2019_MonWedFri.getAllOccurrences();
        checkOccurrences(allOccurrences, Arrays.asList(
            START_MONDAY_31_DEC_2018_10AM,
            WEDNESDAY_02_JAN_2019_10AM,
            FRIDAY_04_JAN_2019_10AM,
            MONDAY_14_JAN_2019_10AM,
            WEDNESDAY_16_JAN_2019_10AM,
            FRIDAY_18_JAN_2019_10AM));

        allOccurrences = biweekly4PM_15Dec2018to10Jan2019_TuesThur.getAllOccurrences();
        checkOccurrences(allOccurrences, Arrays.asList(
            TUESDAY_18_DEC_2018_4PM,
            THURSDAY_20_DEC_2018_4PM,
            TUESDAY_1_JAN_2019_4PM,
            THURSDAY_3_JAN_2019_4PM));

        allOccurrences = biweekly9PM_From22Dec2018to03Feb2019_SatSun.getAllOccurrences();
        checkOccurrences(allOccurrences, Arrays.asList(
            START_SATURDAY_22_DEC_2018_9PM,
            SUNDAY_30_DEC_2018_9PM,
            SATURDAY_05_JAN_2019_9PM,
            SUNDAY_13_JAN_2019_9PM,
            SATURDAY_19_JAN_2019_9PM,
            SUNDAY_27_JAN_2019_9PM,
            SATURDAY_02_FEB_2019_9PM));
    }

    @Test
    public void numberOfOccurencesOfBoundedSchedule_ShouldBeEqualToCountOfAllOccurrences() {
        assertEquals(6, biweekly10AM_31Dec2018to24Jan2019_MonWedFri.getNumberOfOccurences());
        assertEquals(4, biweekly4PM_15Dec2018to10Jan2019_TuesThur.getNumberOfOccurences());
        assertEquals(7, biweekly9PM_From22Dec2018to03Feb2019_SatSun.getNumberOfOccurences());
    }

    private void checkOccurrences(List<LocalDateTime> occurrencesToCheck, List<LocalDateTime> expectedDates) {
        assertEquals(expectedDates.size(), occurrencesToCheck.size());
        for (int i = 0; i < expectedDates.size(); i++) {
            assertEquals(expectedDates.get(i), occurrencesToCheck.get(i));
        }
    }

}
