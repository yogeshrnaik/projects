package com.tomtom.cleancode.examples.solid.isp.task;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ISPTaskExample {

}

interface Task extends Successor, Predecessor {

    Date earliestStart();

    Date latestFinish();
}

interface Successor {

    Date latestStart();
}

interface Predecessor {

    Date earliestFinish();
}

class JiraTask implements Task {

    private final int duration;

    private final List<Predecessor> predecessors;
    private final List<Successor> successors;

    public JiraTask(int duration, List<Predecessor> predecessors, List<Successor> successors) {
        this.duration = duration;
        this.predecessors = predecessors;
        this.successors = successors;
    }

    @Override
    public Date earliestStart() {
        Date result = Date.from(Instant.MIN);
        for (Predecessor task : predecessors)
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
        for (Successor task : successors)
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