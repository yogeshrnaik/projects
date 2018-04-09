package com.tomtom.cleancode.videostore.solution;

import java.util.ArrayList;
import java.util.List;

public class RentalStatement {

    private String customerName;
    private List<Rental> rentals = new ArrayList<Rental>();
    private double totalAmount;
    private int frequentRenterPoints;

    public RentalStatement(String customerName) {
        this.customerName = customerName;
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    public String makeRentalStatement() {
        clearTotals();
        return makeHeader() + makeRentalLines() + makeSummary();
    }

    private void clearTotals() {
        totalAmount = 0;
        frequentRenterPoints = 0;
    }

    private String makeHeader() {
        return "Rental Record for " + getCustomerName() + "\n";
    }

    private String makeRentalLines() {
        String rentalLines = "";

        for (Rental rental : rentals)
            rentalLines += makeRentalLine(rental);

        return rentalLines;
    }

    private String makeRentalLine(Rental rental) {
        double thisAmount = rental.determineAmount();
        frequentRenterPoints += rental.determineFrequentRenterPoints();
        totalAmount += thisAmount;

        return formatRentalLine(rental, thisAmount);
    }

    private String formatRentalLine(Rental rental, double thisAmount) {
        return "\t" + rental.getTitle() + "\t" + thisAmount + "\n";
    }

    private String makeSummary() {
        return "You owed " + totalAmount + "\n" +
            "You earned " + frequentRenterPoints +
            " frequent renter points\n";
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getAmountOwed() {
        return totalAmount;
    }

    public int getFrequentRenterPoints() {
        return frequentRenterPoints;
    }
}