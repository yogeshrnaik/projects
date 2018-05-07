package tdd.examples.cricket;

public class RunsThresholdRule implements PointsCalculationRule {

    private final int runsThreshold;
    private final int bonusPoints;

    public RunsThresholdRule(final int runsThreshold, final int bonusPoints) {
        this.runsThreshold = runsThreshold;
        this.bonusPoints = bonusPoints;
    }

    @Override
    public int calculatePoints(int runsScored) {
        return runsScored >= runsThreshold ? bonusPoints : 0;
    }
}
