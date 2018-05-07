package tdd.examples.interest;

public class Customer {

    public static final int SENIOR_CITIZEN_AGE = 60;
    public static final double SENIOR_CITIZEN_ROI = 9.5;
    public static final double DEFAULT_ROI = 9.0;
    private int age;

    public Customer(int age) {
        this.age = age;
    }

    public boolean isSeniorCitizen() {
        return age >= SENIOR_CITIZEN_AGE;
    }

    public double getRateOfInterest() {
        return isSeniorCitizen() ? SENIOR_CITIZEN_ROI : DEFAULT_ROI;
    }
}
