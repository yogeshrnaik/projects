package tdd.examples.interest;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class SimpleInterestCalculatorTest {

    public static final double DELTA = 0.0001;

    @Test
    public void calculateSimpleInterest() {
        SimpleInterestCalculator calc = new SimpleInterestCalculator();
        RoiProvider provider = new RoiProvider();

        double interest = calc.calculateInterest(1000, provider.getRateOfInterest(), 1);

        assertEquals(80, interest, DELTA);
    }

    @Test
    public void calculateInterestForSeniorCitizen() {
        SimpleInterestCalculator calc = new SimpleInterestCalculator();
        RoiProvider provider = new RoiProvider(new RoiBasedOnCustomerAge(60));

        double interest = calc.calculateInterest(1000, provider.getRateOfInterest(), 1);

        assertEquals(90, interest, DELTA);
    }

    @Test
    public void calculateInterestForNonSeniorCitizen() {
        SimpleInterestCalculator calc = new SimpleInterestCalculator();
        RoiProvider provider = new RoiProvider(new RoiBasedOnCustomerAge(59));

        double interest = calc.calculateInterest(1000, provider.getRateOfInterest(), 1);

        assertEquals(80, interest, DELTA);
    }

    @Test
    public void calculateInterestForHigherPrincipal() {
        SimpleInterestCalculator calc = new SimpleInterestCalculator();
        RoiProvider provider = new RoiProvider(new RoiBasedOnPrincipal(100000));

        double interest = calc.calculateInterest(100000, provider.getRateOfInterest(), 1);

        assertEquals(8500, interest, DELTA);
    }

    @Test
    public void calculateInterestForHigherPrincipalAndSeniorCitizen() {
        SimpleInterestCalculator calc = new SimpleInterestCalculator();
        RoiProvider provider = new RoiProvider(new RoiBasedOnPrincipal(100000), new RoiBasedOnCustomerAge(60));

        double interest = calc.calculateInterest(100000, provider.getRateOfInterest(), 1);

        assertEquals(9500, interest, DELTA);
    }
}
