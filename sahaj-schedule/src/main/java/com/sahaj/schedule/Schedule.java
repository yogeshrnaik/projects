package com.sahaj.schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface Schedule {

    String getEventName();

    LocalDateTime startDate();

    List<LocalDateTime> getOccurrences(int limitNumberOfOccurences);

    List<LocalDateTime> getOccurrencesFrom(LocalDateTime fromDate, int numberOfOccurences);

}
