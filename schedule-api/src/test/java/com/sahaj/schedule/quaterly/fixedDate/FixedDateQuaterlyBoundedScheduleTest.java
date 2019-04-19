package com.sahaj.schedule.quaterly.fixedDate;

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

public class FixedDateQuaterlyBoundedScheduleTest {

    private BoundedSchedule from01Jan2019To30Apr2020On31thOfEveryMonthAt10AM;

    private final String EVENT_NAME_1 = "Event name 1";
    private final String EVENT_NAME_2 = "Event name 2";

    private final LocalDate START_01_JAN_2019 = LocalDate.of(2019, 1, 1);
    private final LocalTime AT_10AM = LocalTime.of(10, 0);
    private final LocalDateTime START_01_JAN_2019_10AM = START_01_JAN_2019.atTime(AT_10AM);

    private final LocalDateTime _31_JAN_2019_10AM = LocalDate.of(2019, 1, 31).atTime(AT_10AM);
    private final LocalDateTime _30_APR_2019_10AM = LocalDate.of(2019, 4, 30).atTime(AT_10AM);
    private final LocalDateTime _31_JUL_2019_10AM = LocalDate.of(2019, 7, 31).atTime(AT_10AM);
    private final LocalDateTime _31_OCT_2019_10AM = LocalDate.of(2019, 10, 31).atTime(AT_10AM);
    private final LocalDateTime _31_JAN_2020_10AM = LocalDate.of(2020, 1, 31).atTime(AT_10AM);
    private final LocalDateTime _30_APR_2020_10AM = LocalDate.of(2020, 4, 30).atTime(AT_10AM);

    private BoundedSchedule from30Nov2018To30Aug2019On30thOfEveryMonthAt4PM;
    private final LocalDate START_30_NOV_2018 = LocalDate.of(2018, 11, 30);
    private final LocalDate END_30_AUG_2019 = LocalDate.of(2019, 8, 30);
    private final LocalTime AT_4PM = LocalTime.of(16, 0);
    private final LocalDateTime START_30_NOV_2018_4PM = START_30_NOV_2018.atTime(AT_4PM);
    private final LocalDateTime _30_JAN_2019_4PM = LocalDate.of(2019, 1, 30).atTime(AT_4PM);
    private final LocalDateTime _30_APR_2019_4PM = LocalDate.of(2019, 4, 30).atTime(AT_4PM);
    private final LocalDateTime _30_JUL_2019_4PM = LocalDate.of(2019, 7, 30).atTime(AT_4PM);
    private final LocalDateTime _30_OCT_2019_4PM = LocalDate.of(2019, 10, 30).atTime(AT_4PM);

    @Before
    public void setup() {
        from01Jan2019To30Apr2020On31thOfEveryMonthAt10AM = ScheduleBuilder.newSchedule(EVENT_NAME_1)
            .quaterly(31)
            .startingOn(START_01_JAN_2019, AT_10AM)
            .endingOn(END_30_AUG_2019);

        from30Nov2018To30Aug2019On30thOfEveryMonthAt4PM = ScheduleBuilder.newSchedule(EVENT_NAME_2)
            .quaterly(30)
            .startingOn(START_30_NOV_2018, AT_4PM)
            .endingOn(END_30_AUG_2019);
    }

    @Test
    public void eventNameIsStoredInSchedule() {
        assertEquals(EVENT_NAME_1, from01Jan2019To30Apr2020On31thOfEveryMonthAt10AM.getEventName());
        assertEquals(EVENT_NAME_2, from30Nov2018To30Aug2019On30thOfEveryMonthAt4PM.getEventName());
    }

    @Test
    public void startDateIsAdjustedToFirstOccurrence() {
        assertEquals(_31_JAN_2019_10AM, from01Jan2019To30Apr2020On31thOfEveryMonthAt10AM.startDate());
        assertEquals(_30_JAN_2019_4PM, from30Nov2018To30Aug2019On30thOfEveryMonthAt4PM.startDate());
    }

    @Test
    public void getOccurrencesWithLimit_ContainsNumberofDatesSameAsProvidedLimitAndFromFirstOccurrence() {
        checkOccurrences(from01Jan2019To30Apr2020On31thOfEveryMonthAt10AM.getOccurrences(5),
            Arrays.asList(
                _31_JAN_2019_10AM,
                _30_APR_2019_10AM));

        checkOccurrences(from30Nov2018To30Aug2019On30thOfEveryMonthAt4PM.getOccurrences(5),
            Arrays.asList(
                _30_JAN_2019_4PM));
    }

    @Test
    public void getOccurrencesFromDateBeforeStartDate_ReturnsOccurrencesFromFirstOccurrence() {
        LocalDateTime _31_DEC_2018_10AM = START_01_JAN_2019_10AM.minusDays(1);
        List<LocalDateTime> occurrencesFrom = from01Jan2019To30Apr2020On31thOfEveryMonthAt10AM.getOccurrencesFrom(_31_DEC_2018_10AM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(
                _31_JAN_2019_10AM,
                _30_APR_2019_10AM));

        LocalDateTime _29_NOV_2018_4PM = START_30_NOV_2018_4PM.minusDays(1);
        occurrencesFrom = from30Nov2018To30Aug2019On30thOfEveryMonthAt4PM.getOccurrencesFrom(_29_NOV_2018_4PM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(
                _30_JAN_2019_4PM));
    }

    @Test
    public void getOccurrencesFromDateSameAsFirstOccurrence_ContainDatesFromFirstOccurrence() {
        List<LocalDateTime> occurrencesFrom =
            from01Jan2019To30Apr2020On31thOfEveryMonthAt10AM.getOccurrencesFrom(_31_JAN_2019_10AM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(_31_JAN_2019_10AM,
                _30_APR_2019_10AM));

        occurrencesFrom = from30Nov2018To30Aug2019On30thOfEveryMonthAt4PM.getOccurrencesFrom(START_30_NOV_2018_4PM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(
                START_30_NOV_2018_4PM,
                _30_JAN_2019_4PM));
    }

    @Test
    public void getOccurrencesFromDateAfterStartDate_ContainDatesOnlyAfterProvidedFromDate() {
        LocalDateTime _01_FEB_2019_10AM = START_01_JAN_2019_10AM.plusDays(31);
        List<LocalDateTime> occurrencesFrom = from01Jan2019To30Apr2020On31thOfEveryMonthAt10AM.getOccurrencesFrom(_01_FEB_2019_10AM, 5);
        checkOccurrences(occurrencesFrom, Arrays.asList(
            _30_APR_2019_10AM));

        LocalDateTime _10_DEC_2018_4PM = START_30_NOV_2018_4PM.plusDays(10);
        occurrencesFrom = from30Nov2018To30Aug2019On30thOfEveryMonthAt4PM.getOccurrencesFrom(_10_DEC_2018_4PM, 5);
        checkOccurrences(occurrencesFrom,
            Arrays.asList(
                _30_JAN_2019_4PM,
                _30_APR_2019_4PM));
    }

    @Test
    public void getOccurrencesFromDateAfterStartDateWithDifferentTime_ContainDatesOnlyAfterProvidedFromDate() {
        LocalDateTime _31_JAN_2019_11AM = START_01_JAN_2019_10AM.plusDays(30).plusHours(1);
        List<LocalDateTime> occurrencesFrom = from01Jan2019To30Apr2020On31thOfEveryMonthAt10AM.getOccurrencesFrom(_31_JAN_2019_11AM, 5);
        checkOccurrences(occurrencesFrom, Arrays.asList(
            _30_APR_2019_10AM));

        LocalDateTime _30_DEC_2018_5PM = START_30_NOV_2018_4PM.plusDays(30).plusHours(1);
        occurrencesFrom = from30Nov2018To30Aug2019On30thOfEveryMonthAt4PM.getOccurrencesFrom(_30_DEC_2018_5PM, 5);
        checkOccurrences(occurrencesFrom, Arrays.asList(
            _30_JAN_2019_4PM,
            _30_APR_2019_4PM));
    }

    @Test
    public void getOccurrencesWithZeroOrNegativeLimit_ReturnsZeroOccurrences() {
        List<LocalDateTime> occurrencesFrom =
            from01Jan2019To30Apr2020On31thOfEveryMonthAt10AM.getOccurrencesFrom(START_01_JAN_2019_10AM, 0);
        assertTrue(occurrencesFrom.isEmpty());

        occurrencesFrom = from30Nov2018To30Aug2019On30thOfEveryMonthAt4PM.getOccurrencesFrom(START_30_NOV_2018_4PM, 0);
        assertTrue(occurrencesFrom.isEmpty());
    }

    @Test
    public void getOccurrencesWithNegativeLimit_ReturnsZeroOccurrences() {
        List<LocalDateTime> occurrencesFrom =
            from01Jan2019To30Apr2020On31thOfEveryMonthAt10AM.getOccurrencesFrom(START_01_JAN_2019_10AM, -1);
        assertTrue(occurrencesFrom.isEmpty());

        occurrencesFrom = from30Nov2018To30Aug2019On30thOfEveryMonthAt4PM.getOccurrencesFrom(START_30_NOV_2018_4PM, -1);
        assertTrue(occurrencesFrom.isEmpty());
    }

    @Test
    public void allOccurencesOfBoundedSchedule_ContainsAllDatesFromFirstTillLastOccurrence() {
        List<LocalDateTime> allOccurrences = from01Jan2019To30Apr2020On31thOfEveryMonthAt10AM.getAllOccurrences();
        checkOccurrences(allOccurrences, Arrays.asList(
            _31_JAN_2019_10AM,
            _30_APR_2019_10AM,
            _31_JUL_2019_10AM));

        allOccurrences = from30Nov2018To30Aug2019On30thOfEveryMonthAt4PM.getAllOccurrences();
        checkOccurrences(allOccurrences, Arrays.asList(
            START_30_NOV_2018_4PM,
            _30_JAN_2019_4PM,
            _30_APR_2019_4PM,
            _30_JUL_2019_4PM));
    }

    @Test
    public void numberOfOccurencesOfBoundedSchedule_ShouldBeEqualToCountOfAllOccurrences() {
        assertEquals(7, from01Jan2019To30Apr2020On31thOfEveryMonthAt10AM.getNumberOfOccurences());
        assertEquals(10, from30Nov2018To30Aug2019On30thOfEveryMonthAt4PM.getNumberOfOccurences());
    }

    private void checkOccurrences(List<LocalDateTime> occurrencesToCheck, List<LocalDateTime> expectedDates) {
        assertEquals(expectedDates.size(), occurrencesToCheck.size());
        for (int i = 0; i < expectedDates.size(); i++) {
            assertEquals(expectedDates.get(i), occurrencesToCheck.get(i));
        }
    }

}
