package com.tomtom.cleancode.examples.solid.isp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ISPExample {

}

interface Task {

    Date earliestStart();

    Date earliestFinish();

    Date latestStart();

    Date latestFinish();
}

class JiraTask implements Task {

    private final int duration;

    private final List<Task> predecessors;
    private final List<Task> successors;

    public JiraTask(int duration, List<Task> predecessors, List<Task> successors) {
        this.duration = duration;
        this.predecessors = predecessors;
        this.successors = successors;
    }

    @Override
    public Date earliestStart() {
        Date result = Date.from(Instant.MIN);
        for (Task task : predecessors)
            if (task.earliestFinish().after(result))
                result = task.earliestFinish();
        return result;
    }

    @Override
    public Date earliestFinish() {
        return addDays(earliestStart(), duration);
    }

    @Override
    public Date latestStart() {
        return minusDays(latestFinish(), duration);
    }

    @Override
    public Date latestFinish() {
        Date result = Date.from(Instant.MAX);
        for (Task task : successors)
            if (task.latestStart().before(result))
                result = task.latestStart();
        return result;
    }

    private Date addDays(Date date, int duration) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        return Date.from(localDateTime.plusDays(duration).atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date minusDays(Date date, int duration) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        return Date.from(localDateTime.minusDays(duration).atZone(ZoneId.systemDefault()).toInstant());
    }

    private LocalDateTime toLocalDateTime(Date date) {
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime;
    }
}
