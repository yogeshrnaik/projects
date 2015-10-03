package tdd.examples.shopping;

public class DefaultPricingRule implements PricingRule {
    @Override
    public double calculatePrice(double unitPrice, int quantity) {
        return quantity * unitPrice;
    }

    @Override
    public double calculateDiscount(double unitPrice, int quantity) {
        return 0;
    }
}
