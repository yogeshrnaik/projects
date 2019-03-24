package com.sahaj.schedule;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

public class NonRepeatingScheduleTest {

    private NonRepeatingSchedule schedule;
    private final Date EXPECTED_SCHEDULED_DATE = new GregorianCalendar(2020, Calendar.FEBRUARY, 12).getTime();

    @Before
    public void setup() {
        Date scheduledDate = new GregorianCalendar(2020, Calendar.FEBRUARY, 12).getTime();
        schedule = new NonRepeatingSchedule("Once", scheduledDate);
    }

    @Test
    public void eventNameIsStoredInNonRepeatingSchedule() {
        assertEquals("Once", schedule.getEventName());
    }

    @Test
    public void startAndEndDateOfNonRepeatingScheduleIsSameAsScheduledDate() {
        assertEquals(EXPECTED_SCHEDULED_DATE, schedule.startDate());
        assertEquals(EXPECTED_SCHEDULED_DATE, schedule.endDate());
    }

    @Test
    public void thereIsOnlyOneOccurrenceOfNonRepeatingSchedule() {
        assertEquals(Arrays.asList(EXPECTED_SCHEDULED_DATE), schedule.getOccurrences(1));
        assertEquals(Arrays.asList(EXPECTED_SCHEDULED_DATE), schedule.getOccurrences(Integer.MAX_VALUE));

        assertEquals(Arrays.asList(EXPECTED_SCHEDULED_DATE), schedule.getAllOccurrences());
        assertEquals(1, schedule.getNumberOfOccurences());
    }

    @Test
    public void nonRepeatingScheduleReturnsOccurenceBeforeScheduleDate() {
        Date dateBeforeScheduledDate = new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime();
        assertEquals(Arrays.asList(EXPECTED_SCHEDULED_DATE), schedule.getOccurrencesFrom(dateBeforeScheduledDate, 1));
        assertEquals(Arrays.asList(EXPECTED_SCHEDULED_DATE), schedule.getOccurrencesFrom(dateBeforeScheduledDate, Integer.MAX_VALUE));
    }

    @Test
    public void nonRepeatingScheduleReturnsNoOccurenceAfterScheduleDate() {
        Date dateAfterScheduledDate = new GregorianCalendar(2020, Calendar.FEBRUARY, 13).getTime();
        assertEquals(Collections.emptyList(), schedule.getOccurrencesFrom(dateAfterScheduledDate, 1));
    }

}
