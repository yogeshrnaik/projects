package com.sahaj.schedule;

import java.util.Date;
import java.util.List;

public interface Schedule {

    String getEventName();

    Date startDate();

    Date endDate();

    List<Date> getOccurrences(int limitNumberOfOccurences);

    List<Date> getOccurrencesFrom(Date startDate, int numberOfOccurences);

    List<Date> getAllOccurrences();

    // # of occurrences for bounded schedules.
    int getNumberOfOccurences();
}
