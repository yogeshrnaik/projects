package com.equalexperts;

public class EarnXPointsForXRunsRule implements PointsCalculationRule {

    @Override
    public int calculatePoints(int runsScored) {
        return runsScored;
    }
}
