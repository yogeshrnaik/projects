package tdd.examples.shopping;

public class Product {

    private final String name;
    private final double unitPrice;
    private PricingRule pricingRule;

    public Product(String name, double unitPrice) {
        this(name, unitPrice, new DefaultPricingRule());
    }

    public Product(String name, double unitPrice, PricingRule pricingRule) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.pricingRule = pricingRule;
    }

    public double applyPricingRule(int quantity) {
        return pricingRule.calculatePrice(unitPrice, quantity);
    }

    public double getDiscount(int quantity) {
        return pricingRule.calculateDiscount(unitPrice, quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return name.equals(product.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
