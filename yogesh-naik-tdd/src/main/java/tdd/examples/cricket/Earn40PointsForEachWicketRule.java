package tdd.examples.cricket;

public class Earn40PointsForEachWicketRule implements PointsBasedOnWicketsRule {

    @Override
    public int calculate(int wickets) {
        return 40 * wickets;
    }
}
