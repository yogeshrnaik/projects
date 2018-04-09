package com.equalexperts;

import java.util.Arrays;
import java.util.List;

public class PointsCalculator implements PointsCalculationRule {

    private final List<PointsCalculationRule> rules;

    public PointsCalculator(PointsCalculationRule... rules) {
        this.rules = Arrays.asList(rules);
    }

    @Override
    public int calculatePoints(int runsScored) {
        return rules.stream().mapToInt(rule -> rule.calculatePoints(runsScored)).sum();
    }

}
