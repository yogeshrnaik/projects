package tdd.examples.cricket;

public interface PointsBasedOnRunsRule extends PointsRule {

    @Override
    public int calculate(int runs);
}