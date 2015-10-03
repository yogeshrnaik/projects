package tdd.examples.shopping;

public class DiscountBasedOnTotalCartPrice implements ShoppingCartPricingRule {

    @Override
    public double calculateTotalPrice(ShoppingCart cart) {
        return 0;
    }

    @Override
    public double calculateTotalDiscount(ShoppingCart cart) {
        return 0;
    }
}
