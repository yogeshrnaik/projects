package com.sahaj.schedule.monthly.fixedDay;

public enum Ordinal {
    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4),
    LAST(5);

    private final int ordinal;

    private Ordinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public int getOrdinal() {
        return this.ordinal;
    }
}
