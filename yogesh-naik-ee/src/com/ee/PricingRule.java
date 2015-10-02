package com.ee;

public interface PricingRule {
    public double calculatePrice(double unitPrice, int quantity);
    public double calculateDiscount(double unitPrice, int quantity);
}
