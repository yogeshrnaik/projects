package com.sahaj.schedule.monthly.fixedDay;

import static com.sahaj.schedule.monthly.fixedDay.Ordinal.FIRST;
import static com.sahaj.schedule.monthly.fixedDay.Ordinal.FOURTH;
import static com.sahaj.schedule.monthly.fixedDay.Ordinal.LAST;
import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
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

public class FixedDayMonthlyBoundedScheduleTest {

    private BoundedSchedule from01JanTo30Aug2019FirstMondayOfEveryMonthAt10AM;

    private final String EVENT_NAME_1 = "Event name 1";
    private final String EVENT_NAME_2 = "Event name 2";
    private final String EVENT_NAME_3 = "Event name 3";

    private final LocalDate START_01_JAN_2019 = LocalDate.of(2019, 1, 1);
    private final LocalTime AT_10AM = LocalTime.of(10, 0);
    private final LocalDateTime START_01_JAN_2019_10AM = START_01_JAN_2019.atTime(AT_10AM);

    private final LocalDateTime _07_JAN_2019_10AM = LocalDate.of(2019, 1, 7).atTime(AT_10AM);
    private final LocalDateTime _04_FEB_2019_10AM = LocalDate.of(2019, 2, 4).atTime(AT_10AM);
    private final LocalDateTime _04_MAR_2019_10AM = LocalDate.of(2019, 3, 4).atTime(AT_10AM);
    private final LocalDateTime _01_APR_2019_10AM = LocalDate.of(2019, 4, 1).atTime(AT_10AM);
    private final LocalDateTime _06_MAY_2019_10AM = LocalDate.of(2019, 5, 6).atTime(AT_10AM);
    private final LocalDateTime _03_JUN_2019_10AM = LocalDate.of(2019, 6, 3).atTime(AT_10AM);
    private final LocalDateTime _01_JUL_2019_10AM = LocalDate.of(2019, 7, 1).atTime(AT_10AM);
    private final LocalDateTime _05_AUG_2019_10AM = LocalDate.of(2019, 8, 5).atTime(AT_10AM);

    private BoundedSchedule from30Nov2018To30Aug2019LastFridayOfEveryMonthAt4PM;
    private final LocalDate START_30_NOV_2018 = LocalDate.of(2018, 11, 30);
    private final LocalDate END_30_AUG_2019 = LocalDate.of(2019, 8, 30);
    private final LocalTime AT_4PM = LocalTime.of(16, 0);
    private final LocalDateTime START_30_NOV_2018_4PM = START_30_NOV_2018.atTime(AT_4PM);
    private final LocalDateTime _30_NOV_2018_4PM = LocalDate.of(2018, 11, 30).atTime(AT_4PM);
    private final LocalDateTime _28_DEC_2018_4PM = LocalDate.of(2018, 12, 28).atTime(AT_4PM);
    private final LocalDateTime _25_JAN_2019_4PM = LocalDate.of(2019, 1, 25).atTime(AT_4PM);
    private final LocalDateTime _22_FEB_2019_4PM = LocalDate.of(2019, 2, 22).atTime(AT_4PM);
    private final LocalDateTime _29_MAR_2019_4PM = LocalDate.of(2019, 3, 29).atTime(AT_4PM);
    private final LocalDateTime _26_APR_2019_4PM = LocalDate.of(2019, 4, 26).atTime(AT_4PM);
    private final LocalDateTime _31_MAY_2019_4PM = LocalDate.of(2019, 5, 31).atTime(AT_4PM);
    private final LocalDateTime _28_JUN_2019_4PM = LocalDate.of(2019, 6, 28).atTime(AT_4PM);
    private final LocalDateTime _26_JUL_2019_4PM = LocalDate.of(2019, 7, 26).atTime(AT_4PM);
    private final LocalDateTime _30_AUG_2019_4PM = LocalDate.of(2019, 8, 30).atTime(AT_4PM);

    private BoundedSchedule from24Aug2019To31Jan2020FourthSaturdayOfEveryMonthAt4PM;
    private final LocalDate START_24_AUG_2019 = LocalDate.of(2019, 8, 24);
    private final LocalDate END_31_JAN_2020 = LocalDate.of(2020, 1, 31);
    private final LocalDateTime START_24_AUG_2019_4PM = START_24_AUG_2019.atTime(AT_4PM);
    private final LocalDateTime _24_AUG_2019_4PM = LocalDate.of(2019, 8, 24).atTime(AT_4PM);
    private final LocalDateTime _28_SEP_2019_4PM = LocalDate.of(2019, 9, 28).atTime(AT_4PM);
    private final LocalDateTime _26_OCT_2019_4PM = LocalDate.of(2019, 10, 26).atTime(AT_4PM);
    private final LocalDateTime _23_NOV_2019_4PM = LocalDate.of(2019, 11, 23).atTime(AT_4PM);
    private final LocalDateTime _28_DEC_2019_4PM = LocalDate.of(2019, 12, 28).atTime(AT_4PM);
    private final LocalDateTime _25_JAN_2020_4PM = LocalDate.of(2020, 1, 25).atTime(AT_4PM);

    @Before
    public void setup() {
        from01JanTo30Aug2019FirstMondayOfEveryMonthAt10AM = ScheduleBuilder.newSchedule(EVENT_NAME_1)
            .monthly(FIRST, MONDAY)
            .startingOn(START_01_JAN_2019, AT_10AM)
            .endingOn(END_30_AUG_2019);

        from30Nov2018To30Aug2019LastFridayOfEveryMonthAt4PM = ScheduleBuilder.newSchedule(EVENT_NAME_2)
            .monthly(LAST, FRIDAY)
            .startingOn(START_30_NOV_2018, AT_4PM)
            .endingOn(END_30_AUG_2019);

        from24Aug2019To31Jan2020FourthSaturdayOfEveryMonthAt4PM = ScheduleBuilder.newSchedule(EVENT_NAME_3)
            .monthly(FOURTH, SATURDAY)
            .startingOn(START_24_AUG_2019, AT_4PM)
            .endingOn(END_31_JAN_2020);
    }

    @Test
    public void eventNameIsStoredInSchedule() {
        assertEquals(EVENT_NAME_1, from01JanTo30Aug2019FirstMondayOfEveryMonthAt10AM.getEventName());
        assertEquals(EVENT_NAME_2, from30Nov2018To30Aug2019LastFridayOfEveryMonthAt4PM.getEventName());
        assertEquals(EVENT_NAME_3, from24Aug2019To31Jan2020FourthSaturdayOfEveryMonthAt4PM.getEventName());
    }

    @Test
    public void startDateIsAdjustedToFirstOccurrence() {
        assertEquals(_07_JAN_2019_10AM, from01JanTo30Aug2019FirstMondayOfEveryMonthAt10AM.startDate());
        assertEquals(_30_NOV_2018_4PM, from30Nov2018To30Aug2019LastFridayOfEveryMonthAt4PM.startDate());
        assertEquals(_24_AUG_2019_4PM, from24Aug2019To31Jan2020FourthSaturdayOfEveryMonthAt4PM.startDate());
    }

    @Test
    public void getOccurrencesWithLimit_ContainsNumberofDatesSameAsProvidedLimitAndFromFirstOccurrence() {
        checkOccurrences(from01JanTo30Aug2019FirstMondayOfEveryMonthAt10AM.getOccurrences(5),
            Arrays.asList(
                _07_JAN_2019_10AM,
                _04_FEB_2019_10AM,
                _04_MAR_2019_10AM,
                _01_APR_2019_10AM,
                _06_MAY_2019_10AM));

        checkOccurrences(from30Nov2018To30Aug2019LastFridayOfEveryMonthAt4PM.getOccurrences(5),
            Arrays.asList(
                _30_NOV_2018_4PM,
                _28_DEC_2018_4PM,
                _25_JAN_2019_4PM,
                _22_FEB_2019_4PM,
                _29_MAR_2019_4PM));

        checkOccurrences(from24Aug2019To31Jan2020FourthSaturdayOfEveryMonthAt4PM.getOccurrences(5),
            Arrays.asList(
                _24_AUG_2019_4PM,
                _28_SEP_2019_4PM,
                _26_OCT_2019_4PM,
                _23_NOV_2019_4PM,
                _28_DEC_2019_4PM));
    }

    @Test
    public void getOccurrencesFromDateBeforeStartDate_ReturnsOccurrencesFromFirstOccurrence() {
        LocalDateTime _31_DEC_2018_10AM = START_01_JAN_2019_10AM.minusDays(1);
        List<LocalDateTime> occurrencesFrom = from01JanTo30Aug2019FirstMondayOfEveryMonthAt10AM.getOccurrencesFrom(_31_DEC_2018_10AM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(
                _07_JAN_2019_10AM,
                _04_FEB_2019_10AM,
                _04_MAR_2019_10AM,
                _01_APR_2019_10AM,
                _06_MAY_2019_10AM));

        LocalDateTime _29_NOV_2018_4PM = START_30_NOV_2018_4PM.minusDays(1);
        occurrencesFrom = from30Nov2018To30Aug2019LastFridayOfEveryMonthAt4PM.getOccurrencesFrom(_29_NOV_2018_4PM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(
                _30_NOV_2018_4PM,
                _28_DEC_2018_4PM,
                _25_JAN_2019_4PM,
                _22_FEB_2019_4PM,
                _29_MAR_2019_4PM));

        LocalDateTime _23_AUG_2019_4PM = START_24_AUG_2019_4PM.minusDays(1);
        occurrencesFrom = from24Aug2019To31Jan2020FourthSaturdayOfEveryMonthAt4PM.getOccurrencesFrom(_23_AUG_2019_4PM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(
                _24_AUG_2019_4PM,
                _28_SEP_2019_4PM,
                _26_OCT_2019_4PM,
                _23_NOV_2019_4PM,
                _28_DEC_2019_4PM));
    }

    @Test
    public void getOccurrencesFromDateSameAsFirstOccurrence_ContainDatesFromFirstOccurrence() {
        List<LocalDateTime> occurrencesFrom =
            from01JanTo30Aug2019FirstMondayOfEveryMonthAt10AM.getOccurrencesFrom(_07_JAN_2019_10AM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(_07_JAN_2019_10AM,
                _04_FEB_2019_10AM,
                _04_MAR_2019_10AM,
                _01_APR_2019_10AM,
                _06_MAY_2019_10AM));

        occurrencesFrom = from30Nov2018To30Aug2019LastFridayOfEveryMonthAt4PM.getOccurrencesFrom(START_30_NOV_2018_4PM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(
                _30_NOV_2018_4PM,
                _28_DEC_2018_4PM,
                _25_JAN_2019_4PM,
                _22_FEB_2019_4PM,
                _29_MAR_2019_4PM));

        occurrencesFrom = from24Aug2019To31Jan2020FourthSaturdayOfEveryMonthAt4PM.getOccurrencesFrom(START_24_AUG_2019_4PM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(
                _24_AUG_2019_4PM,
                _28_SEP_2019_4PM,
                _26_OCT_2019_4PM,
                _23_NOV_2019_4PM,
                _28_DEC_2019_4PM));
    }

    @Test
    public void getOccurrencesFromDateAfterStartDate_ContainDatesOnlyAfterProvidedFromDate() {
        LocalDateTime _01_FEB_2019_10AM = START_01_JAN_2019_10AM.plusDays(31);
        List<LocalDateTime> occurrencesFrom = from01JanTo30Aug2019FirstMondayOfEveryMonthAt10AM.getOccurrencesFrom(_01_FEB_2019_10AM, 5);
        checkOccurrences(occurrencesFrom, Arrays.asList(
            _04_FEB_2019_10AM,
            _04_MAR_2019_10AM,
            _01_APR_2019_10AM,
            _06_MAY_2019_10AM,
            _03_JUN_2019_10AM));

        LocalDateTime _10_DEC_2018_4PM = START_30_NOV_2018_4PM.plusDays(10);
        occurrencesFrom = from30Nov2018To30Aug2019LastFridayOfEveryMonthAt4PM.getOccurrencesFrom(_10_DEC_2018_4PM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(
                _28_DEC_2018_4PM,
                _25_JAN_2019_4PM,
                _22_FEB_2019_4PM,
                _29_MAR_2019_4PM,
                _26_APR_2019_4PM));

        LocalDateTime _28_SEP_2019_4PM = START_24_AUG_2019_4PM.plusDays(35);
        occurrencesFrom = from24Aug2019To31Jan2020FourthSaturdayOfEveryMonthAt4PM.getOccurrencesFrom(_28_SEP_2019_4PM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(
                _28_SEP_2019_4PM,
                _26_OCT_2019_4PM,
                _23_NOV_2019_4PM,
                _28_DEC_2019_4PM,
                _25_JAN_2020_4PM));
    }

    @Test
    public void getOccurrencesFromDateAfterStartDateWithDifferentTime_ContainDatesOnlyAfterProvidedFromDate() {
        LocalDateTime _31_JAN_2019_11AM = START_01_JAN_2019_10AM.plusDays(30).plusHours(1);
        List<LocalDateTime> occurrencesFrom = from01JanTo30Aug2019FirstMondayOfEveryMonthAt10AM.getOccurrencesFrom(_31_JAN_2019_11AM, 5);
        checkOccurrences(occurrencesFrom, Arrays.asList(
            _04_FEB_2019_10AM,
            _04_MAR_2019_10AM,
            _01_APR_2019_10AM,
            _06_MAY_2019_10AM,
            _03_JUN_2019_10AM));

        LocalDateTime _28_DEC_2018_5PM = START_30_NOV_2018_4PM.plusDays(28).plusHours(1);
        occurrencesFrom = from30Nov2018To30Aug2019LastFridayOfEveryMonthAt4PM.getOccurrencesFrom(_28_DEC_2018_5PM, 5);
        checkOccurrences(occurrencesFrom, Arrays.asList(
            _25_JAN_2019_4PM,
            _22_FEB_2019_4PM,
            _29_MAR_2019_4PM,
            _26_APR_2019_4PM,
            _31_MAY_2019_4PM));

        LocalDateTime _28_SEP_2019_5PM = START_24_AUG_2019_4PM.plusDays(35).plusHours(1);
        occurrencesFrom = from24Aug2019To31Jan2020FourthSaturdayOfEveryMonthAt4PM.getOccurrencesFrom(_28_SEP_2019_5PM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(
                _26_OCT_2019_4PM,
                _23_NOV_2019_4PM,
                _28_DEC_2019_4PM,
                _25_JAN_2020_4PM));
    }

    @Test
    public void getOccurrencesWithZeroOrNegativeLimit_ReturnsZeroOccurrences() {
        List<LocalDateTime> occurrencesFrom =
            from01JanTo30Aug2019FirstMondayOfEveryMonthAt10AM.getOccurrencesFrom(START_01_JAN_2019_10AM, 0);
        assertTrue(occurrencesFrom.isEmpty());

        occurrencesFrom = from30Nov2018To30Aug2019LastFridayOfEveryMonthAt4PM.getOccurrencesFrom(START_30_NOV_2018_4PM, 0);
        assertTrue(occurrencesFrom.isEmpty());

        occurrencesFrom = from24Aug2019To31Jan2020FourthSaturdayOfEveryMonthAt4PM.getOccurrencesFrom(START_24_AUG_2019_4PM, 0);
        assertTrue(occurrencesFrom.isEmpty());
    }

    @Test
    public void getOccurrencesWithNegativeLimit_ReturnsZeroOccurrences() {
        List<LocalDateTime> occurrencesFrom =
            from01JanTo30Aug2019FirstMondayOfEveryMonthAt10AM.getOccurrencesFrom(START_01_JAN_2019_10AM, -1);
        assertTrue(occurrencesFrom.isEmpty());

        occurrencesFrom = from30Nov2018To30Aug2019LastFridayOfEveryMonthAt4PM.getOccurrencesFrom(START_30_NOV_2018_4PM, -1);
        assertTrue(occurrencesFrom.isEmpty());

        occurrencesFrom = from24Aug2019To31Jan2020FourthSaturdayOfEveryMonthAt4PM.getOccurrencesFrom(START_24_AUG_2019_4PM, -1);
        assertTrue(occurrencesFrom.isEmpty());
    }

    @Test
    public void allOccurencesOfBoundedSchedule_ContainsAllDatesFromFirstTillLastOccurrence() {
        List<LocalDateTime> allOccurrences = from01JanTo30Aug2019FirstMondayOfEveryMonthAt10AM.getAllOccurrences();
        checkOccurrences(allOccurrences, Arrays.asList(
            _07_JAN_2019_10AM,
            _04_FEB_2019_10AM,
            _04_MAR_2019_10AM,
            _01_APR_2019_10AM,
            _06_MAY_2019_10AM,
            _03_JUN_2019_10AM,
            _01_JUL_2019_10AM,
            _05_AUG_2019_10AM));

        allOccurrences = from30Nov2018To30Aug2019LastFridayOfEveryMonthAt4PM.getAllOccurrences();
        checkOccurrences(allOccurrences, Arrays.asList(
            _30_NOV_2018_4PM,
            _28_DEC_2018_4PM,
            _25_JAN_2019_4PM,
            _22_FEB_2019_4PM,
            _29_MAR_2019_4PM,
            _26_APR_2019_4PM,
            _31_MAY_2019_4PM,
            _28_JUN_2019_4PM,
            _26_JUL_2019_4PM,
            _30_AUG_2019_4PM));

        allOccurrences = from24Aug2019To31Jan2020FourthSaturdayOfEveryMonthAt4PM.getAllOccurrences();
        checkOccurrences(allOccurrences, Arrays.asList(
            _24_AUG_2019_4PM,
            _28_SEP_2019_4PM,
            _26_OCT_2019_4PM,
            _23_NOV_2019_4PM,
            _28_DEC_2019_4PM,
            _25_JAN_2020_4PM));
    }

    @Test
    public void numberOfOccurencesOfBoundedSchedule_ShouldBeEqualToCountOfAllOccurrences() {
        assertEquals(8, from01JanTo30Aug2019FirstMondayOfEveryMonthAt10AM.getNumberOfOccurences());
        assertEquals(10, from30Nov2018To30Aug2019LastFridayOfEveryMonthAt4PM.getNumberOfOccurences());
        assertEquals(6, from24Aug2019To31Jan2020FourthSaturdayOfEveryMonthAt4PM.getNumberOfOccurences());
    }

    private void checkOccurrences(List<LocalDateTime> occurrencesToCheck, List<LocalDateTime> expectedDates) {
        assertEquals(expectedDates.size(), occurrencesToCheck.size());
        for (int i = 0; i < expectedDates.size(); i++) {
            assertEquals(expectedDates.get(i), occurrencesToCheck.get(i));
        }
    }

}
