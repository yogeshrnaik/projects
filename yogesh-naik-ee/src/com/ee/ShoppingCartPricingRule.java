package com.ee;

public interface ShoppingCartPricingRule {
    public double calculateTotalPrice(ShoppingCart cart);
    public double calculateTotalDiscount(ShoppingCart cart);

}
