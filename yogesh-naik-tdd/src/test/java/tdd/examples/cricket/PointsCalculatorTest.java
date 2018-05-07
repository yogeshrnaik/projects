package tdd.examples.cricket;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class PointsCalculatorTest {

    private PointsCalculator pointsCalculator;

    @Before
    public void setup() {
        pointsCalculator = new PointsCalculator(
            new RunRules(
                new EarnXPointsForXRunsRule(),
                new DuckRule(),
                new RunsThresholdRule(25, 10),
                new RunsThresholdRule(50, 25),
                new RunsThresholdRule(75, 20),
                new RunsThresholdRule(100, 40)),
            new WicketRules(new Earn40PointsForEachWicketRule()));
    }

    @Test
    public void playerEarnsOnePointForOneRunScored() {
        assertEquals(1, pointsCalculator.calculate(new Player(1, 0, 0)));
    }

    @Test
    public void playerEarnsXPointsForXRunScored() {
        assertEquals(2, pointsCalculator.calculate(new Player(2, 0, 0)));
    }

    @Test
    public void playerEarns10BonusPointsForScoring25Runs() {
        assertEquals(35, pointsCalculator.calculate(new Player(25, 0, 0)));
    }

    @Test
    public void playerEarnsNegtive5PointsForScoringDuck() {
        assertEquals(-5, pointsCalculator.calculate(new Player(0, 0, 0)));
    }

    @Test
    public void playerEarns50BonusPointsForScoring50Runs() {
        assertEquals(85, pointsCalculator.calculate(new Player(50, 0, 0)));
    }

    @Test
    public void playerEarns20BonusPointsForScoring75Runs() {
        assertEquals(130, pointsCalculator.calculate(new Player(75, 0, 0)));
    }

    @Test
    public void playerEarns40BonusPointsForScoring100Runs() {
        assertEquals(195, pointsCalculator.calculate(new Player(100, 0, 0)));
    }

    @Test
    public void playerEarns40PointsForEachWicket() {
        pointsCalculator = new PointsCalculator(new WicketRules(new Earn40PointsForEachWicketRule()));
        assertEquals(40, pointsCalculator.calculate(new Player(0, 1, 0)));
    }
}
