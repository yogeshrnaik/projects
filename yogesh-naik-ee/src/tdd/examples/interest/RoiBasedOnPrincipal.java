package tdd.examples.interest;

public class RoiBasedOnPrincipal implements RoiCriteria {

    private final double principal;

    public RoiBasedOnPrincipal(double principal) {
        this.principal = principal;
    }


    @Override
    public boolean isApplicable() {
        return principal >= 100000;
    }

    @Override
    public double getBonusRateOfInterest() {
        return 0.5;
    }
}
