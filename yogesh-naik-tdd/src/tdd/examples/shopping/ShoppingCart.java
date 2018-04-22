package tdd.examples.shopping;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private List<CartItem> cartItems;
    private double totalPrice;
    private double discount;

    public ShoppingCart() {
        this.cartItems = new ArrayList<>();
    }

    public void addProduct(Product product, int quantity) {
        CartItem item = new CartItem(product, quantity);
        cartItems.add(item);
        totalPrice += item.getTotalPrice();
        discount += item.getDiscount();
    }

    public int getProductQuantity(Product product) {
        for (CartItem item : cartItems) {
            if (item.getProduct().equals(product)) {
                return item.getQuantity();
            }
        }
        return 0;
    }

    public double getProductDiscount(Product product) {
        return product.getDiscount(getProductQuantity(product));
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public double getProductTotalPrice(Product product) {
        return product.applyPricingRule(getProductQuantity(product));
    }
}
