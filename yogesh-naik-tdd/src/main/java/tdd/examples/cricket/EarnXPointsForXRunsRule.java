package tdd.examples.cricket;

public class EarnXPointsForXRunsRule implements PointsBasedOnRunsRule {

    @Override
    public int calculate(int runs) {
        return runs;
    }
}
