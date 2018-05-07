package tdd.examples.shopping;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ShoppingCartTest {

    public static final double DELTA = 0.0001;

    @Test
    public void addProductToEmptyShoppingCart() {
        ShoppingCart cart = new ShoppingCart();
        Product dove = new Product("Dove Soap", 30);

        cart.addProduct(dove, 5);

        assertEquals(5, cart.getProductQuantity(dove));
        assertEquals(150, cart.getTotalPrice(), DELTA);
    }

    @Test
    public void buy2get1free() {
        ShoppingCart cart = new ShoppingCart();
        Product dove = new Product("Dove Soap", 30, new BuyXGetYFreeRule(2, 1));

        cart.addProduct(dove, 3);

        assertEquals(3, cart.getProductQuantity(dove));
        assertEquals(30, cart.getDiscount(), DELTA);
        assertEquals(60, cart.getTotalPrice(), DELTA);
    }

    @Test
    public void buy5DoveSoap() {
        ShoppingCart cart = new ShoppingCart();
        Product dove = new Product("Dove Soap", 30, new BuyXGetYFreeRule(2, 1));

        cart.addProduct(dove, 5);

        assertEquals(5, cart.getProductQuantity(dove));
        assertEquals(30, cart.getDiscount(), DELTA);
        assertEquals(120, cart.getTotalPrice(), DELTA);
    }

    @Test
    public void buy3Doves2AxeDeos() {
        ShoppingCart cart = new ShoppingCart();
        Product dove = new Product("Dove Soap", 30, new BuyXGetYFreeRule(2, 1));
        Product axeDeo = new Product("Axe Deo", 100);

        cart.addProduct(dove, 3);
        cart.addProduct(axeDeo, 2);

        assertEquals(3, cart.getProductQuantity(dove));
        assertEquals(2, cart.getProductQuantity(axeDeo));
        assertEquals(30, cart.getDiscount(), DELTA);
        assertEquals(260, cart.getTotalPrice(), DELTA);
    }

    @Test
    public void buy1Get50PercentageOffOnNextOne() {
        ShoppingCart cart = new ShoppingCart();
        Product dove = new Product("Dove Soap", 30, new Buy1Get50PercentOffOnNext());

        cart.addProduct(dove, 2);

        assertEquals(2, cart.getProductQuantity(dove));
        assertEquals(15, cart.getDiscount(), DELTA);
        assertEquals(45, cart.getTotalPrice(), DELTA);
    }

    @Test
    public void applyDiscountBasedOnTotalPriceOfCart() {
        ShoppingCart cart = new ShoppingCart();
        Product dove = new Product("Dove Soap", 30, new BuyXGetYFreeRule(2, 1));
        Product axeDeo = new Product("Axe Deo", 100);

        cart.addProduct(dove, 5);
        cart.addProduct(axeDeo, 4);

        assertEquals(5, cart.getProductQuantity(dove));
        assertEquals(4, cart.getProductQuantity(axeDeo));
        assertEquals(30, cart.getProductDiscount(dove), DELTA);
        assertEquals(120, cart.getProductTotalPrice(dove), DELTA);

        assertEquals(80, cart.getProductDiscount(axeDeo), DELTA);
        assertEquals(320, cart.getProductTotalPrice(axeDeo), DELTA);

        assertEquals(110, cart.getDiscount(), DELTA);
        assertEquals(440, cart.getTotalPrice(), DELTA);
    }

}
