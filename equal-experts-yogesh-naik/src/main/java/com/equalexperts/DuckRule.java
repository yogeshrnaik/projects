package com.equalexperts;

public class DuckRule implements PointsCalculationRule {

    private static final int DUCK = 0;
    private static final int NEGATIVE_POINTS_FOR_DUCK = -5;

    @Override
    public int calculatePoints(int runsScored) {
        return runsScored == DUCK ? NEGATIVE_POINTS_FOR_DUCK : 0;
    }
}
