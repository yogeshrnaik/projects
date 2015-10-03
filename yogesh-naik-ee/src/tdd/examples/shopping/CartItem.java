package tdd.examples.shopping;

public class CartItem {

    private Product product;
    private int quantity;
    private double discount;
    private double totalPrice;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.discount = product.getDiscount(quantity);
        this.totalPrice = product.applyPricingRule(quantity);
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
