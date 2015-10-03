package tdd.examples.shopping;

public class BuyXGetYFreeRule implements PricingRule {

    private final int buyX;
    private final int getY;

    public BuyXGetYFreeRule(int buyX, int getY) {
        this.buyX = buyX;
        this.getY = getY;
    }

    @Override
    public double calculatePrice(double unitPrice, int quantity) {
        return (quantity * unitPrice) - calculateDiscount(unitPrice, quantity);

    }

    @Override
    public double calculateDiscount(double unitPrice, int quantity) {
        return (quantity / (buyX + getY)) * unitPrice;
    }
}
