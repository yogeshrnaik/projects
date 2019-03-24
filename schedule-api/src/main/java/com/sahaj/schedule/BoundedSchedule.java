package com.sahaj.schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface BoundedSchedule extends Schedule {

    LocalDateTime endDate();

    List<LocalDateTime> getAllOccurrences();

    int getNumberOfOccurences();
}
