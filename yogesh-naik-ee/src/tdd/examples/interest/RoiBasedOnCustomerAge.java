package tdd.examples.interest;

public class RoiBasedOnCustomerAge implements  RoiCriteria {

    private final int age;

    public RoiBasedOnCustomerAge(int age) {
        this.age = age;
    }
    @Override
    public boolean isApplicable() {
        return age >= 60;
    }

    @Override
    public double getBonusRateOfInterest() {
        return 1;
    }
}
