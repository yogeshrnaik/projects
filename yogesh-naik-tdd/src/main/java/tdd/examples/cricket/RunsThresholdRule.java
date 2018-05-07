package tdd.examples.cricket;

public class RunsThresholdRule implements PointsBasedOnRunsRule {

    private final int runsThreshold;
    private final int bonusPoints;

    public RunsThresholdRule(final int runsThreshold, final int bonusPoints) {
        this.runsThreshold = runsThreshold;
        this.bonusPoints = bonusPoints;
    }

    @Override
    public int calculate(int runs) {
        return runs >= runsThreshold ? bonusPoints : 0;
    }
}
