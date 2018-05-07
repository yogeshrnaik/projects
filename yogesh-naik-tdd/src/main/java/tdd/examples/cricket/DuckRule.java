package tdd.examples.cricket;

public class DuckRule implements PointsBasedOnRunsRule {

    private static final int DUCK = 0;
    private static final int NEGATIVE_POINTS_FOR_DUCK = -5;

    @Override
    public int calculate(int runs) {
        return runs == DUCK ? NEGATIVE_POINTS_FOR_DUCK : 0;
    }
}
