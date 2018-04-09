package com.equalexperts;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PointsCalculatorTest {

    private PointsCalculator pointsCalculator;

    @Before
    public void setup() {
        pointsCalculator = new PointsCalculator(new EarnXPointsForXRunsRule(),
            new DuckRule(),
            new RunsThresholdRule(25, 10),
            new RunsThresholdRule(50, 25),
            new RunsThresholdRule(75, 20),
            new RunsThresholdRule(100, 40));
    }

    @Test
    public void playerEarnsOnePointForOneRunScored() {
        Assert.assertEquals(1, pointsCalculator.calculatePoints(1));
    }

    @Test
    public void playerEarnsXPointsForXRunScored() {
        Assert.assertEquals(2, pointsCalculator.calculatePoints(2));
    }

    @Test
    public void playerEarns10BonusPointsForScoring25Runs() {
        Assert.assertEquals(35, pointsCalculator.calculatePoints(25));
    }

    @Test
    public void playerEarnsNegtive5PointsForScoringDuck() {
        Assert.assertEquals(-5, pointsCalculator.calculatePoints(0));
    }

    @Test
    public void playerEarns50BonusPointsForScoring50Runs() {
        Assert.assertEquals(85, pointsCalculator.calculatePoints(50));
    }

    @Test
    public void playerEarns20BonusPointsForScoring75Runs() {
        Assert.assertEquals(130, pointsCalculator.calculatePoints(75));
    }

    @Test
    public void playerEarns40BonusPointsForScoring100Runs() {
        Assert.assertEquals(195, pointsCalculator.calculatePoints(100));
    }

    @Test
    public void playerEarns40PointsForEachWicket() {
        Assert.assertEquals(40, pointsCalculator.calculatePoints(100));
    }
}
