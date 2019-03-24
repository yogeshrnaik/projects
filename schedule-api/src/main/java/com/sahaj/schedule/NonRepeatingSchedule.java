package com.sahaj.schedule;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class NonRepeatingSchedule extends AbstractSchedule {

    private final Date scheduledDate;

    public NonRepeatingSchedule(String eventName, Date scheduledDate) {
        super(eventName);
        this.scheduledDate = scheduledDate;
    }

    @Override
    public Date startDate() {
        return scheduledDate;
    }

    @Override
    public Date endDate() {
        return scheduledDate;
    }

    @Override
    public List<Date> getOccurrences(int limitNumberOfOccurences) {
        return Arrays.asList(scheduledDate);
    }

    @Override
    public List<Date> getOccurrencesFrom(Date startDate, int numberOfOccurences) {
        return scheduledDate.after(startDate)
            ? getOccurrences(numberOfOccurences)
            : Collections.emptyList();
    }

    @Override
    public List<Date> getAllOccurrences() {
        return getOccurrences(1);
    }

    @Override
    public int getNumberOfOccurences() {
        return 1;
    }
}
