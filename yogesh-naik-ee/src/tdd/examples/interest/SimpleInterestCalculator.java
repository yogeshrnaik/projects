package tdd.examples.interest;

public class SimpleInterestCalculator {
    public double calculateInterest(double principal, double roi, int year) {
        return principal * roi/100 * year;
    }
}
