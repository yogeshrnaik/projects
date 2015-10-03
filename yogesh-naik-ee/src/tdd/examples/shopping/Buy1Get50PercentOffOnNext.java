package tdd.examples.shopping;

public class Buy1Get50PercentOffOnNext implements PricingRule {

    @Override
    public double calculatePrice(double unitPrice, int quantity) {
        return unitPrice * quantity - calculateDiscount(unitPrice, quantity);
    }

    @Override
    public double calculateDiscount(double unitPrice, int quantity) {
        if (quantity <= 1) {
            return 0;
        } else {
            return (quantity / 2) * unitPrice / 2;
        }
    }
}
