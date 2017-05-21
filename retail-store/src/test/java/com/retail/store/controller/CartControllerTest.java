package com.retail.store.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import com.retail.store.RetailStoreApplication;
import com.retail.store.dto.CartUpdateDto;
import com.retail.store.dto.ResponseType;
import com.retail.store.model.Product;
import com.retail.store.model.ProductCategory;
import com.retail.store.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RetailStoreApplication.class)
@WebAppConfiguration
public class CartControllerTest extends RetailStoreControllerTest {

    private static final String CART_URL = "/users/%s/cart";

    private User user = new User("Yogesh");

    private ProductCategory cat1 = new ProductCategory(null, "A", 10.0);
    private Product product1 = new Product(null, "Product 1A", 10.0, cat1);

    private ProductCategory cat2 = new ProductCategory(null, "B", 20.0);
    private Product product2 = new Product(null, "Product 2B", 20.0, cat2);

    private ProductCategory cat3 = new ProductCategory(null, "C", 0.0);
    private Product product3 = new Product(null, "Product 2B", 30.0, cat3);

    @Override
    @Before
    public void setUp() {
        super.setUp();

        userRepo.saveAndFlush(user);
        categoryRepo.save(Arrays.asList(cat1, cat2, cat3));
        productRepo.save(Arrays.asList(product1, product2, product3));
    }

    @Test
    public void getCartByUserId_ReturnsEmptyCartByDefault() throws Exception {
        verifyEmptyCart(user.getId());
    }

    private void verifyEmptyCart(Long userId) throws Exception {
        mockMvc.perform(get(String.format(CART_URL, userId)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$.userId", is(userId.intValue())))
            .andExpect(jsonPath("$.totalPriceBeforeTax", is(0.0)))
            .andExpect(jsonPath("$.totalSalesTax", is(0.0)))
            .andExpect(jsonPath("$.cartItems", hasSize(0)));
    }

    @Test
    public void addProductToCart_UserNotFound() throws Exception {
        String cartUpdateJson = json(new CartUpdateDto(Long.valueOf(0), product1.getId(), 5));

        String cartUrl = String.format(CART_URL, 0);
        mockMvc.perform(post(cartUrl)
            .contentType(contentType)
            .content(cartUpdateJson))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.type", is(ResponseType.ERROR.toString())))
            .andExpect(jsonPath("$.message", is(response.getMessage("NotFound.user"))));
    }

    @Test
    public void addProductToCart_ProductNotFound() throws Exception {
        String cartUpdateJson = json(new CartUpdateDto(user.getId(), Long.valueOf(0), 5));

        String cartUrl = String.format(CART_URL, user.getId());
        mockMvc.perform(post(cartUrl)
            .contentType(contentType)
            .content(cartUpdateJson))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.type", is(ResponseType.ERROR.toString())))
            .andExpect(jsonPath("$.message", is(response.getMessage("NotFound.product"))));
    }

    @Test
    public void addProductToCart_InvalidQuantity() throws Exception {
        String cartUpdateJson = json(new CartUpdateDto(user.getId(), product1.getId(), -1));

        String cartUrl = String.format(CART_URL, user.getId());
        mockMvc.perform(post(cartUrl)
            .contentType(contentType)
            .content(cartUpdateJson))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.type", is(ResponseType.ERROR.toString())))
            .andExpect(jsonPath("$.message", is(response.getMessage("Min.cartupdatedto.quantity"))));
    }

    @Test
    public void addProductToCart_WithValidValuesIsSuccessful() throws Exception {
        final int PRODUCT_1_QUANTITY = 5;
        final int PRODUCT_2_QUANTITY = 10;

        addProductToCart(user, product1, PRODUCT_1_QUANTITY);
        addProductToCart(user, product2, PRODUCT_2_QUANTITY);

        // retrieve cart and verify that it is updated with correct values
        ResultActions result = mockMvc
            .perform(get(String.format(CART_URL, user.getId())))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$.userId", is(user.getId().intValue())))
            .andExpect(jsonPath("$.totalPriceBeforeTax",
                is(product1.calculatePrice(PRODUCT_1_QUANTITY) + product2.calculatePrice(PRODUCT_2_QUANTITY))))
            .andExpect(jsonPath("$.totalSalesTax",
                is(product1.calculateSalesTax(PRODUCT_1_QUANTITY) + product2.calculateSalesTax(PRODUCT_2_QUANTITY))))
            .andExpect(jsonPath("$.grandTotal",
                is(product1.calculateTotalPrice(PRODUCT_1_QUANTITY) + product2.calculateTotalPrice(PRODUCT_2_QUANTITY))))
            .andExpect(jsonPath("$.cartItems", hasSize(2)));

        verifyCartItem(result, product1, PRODUCT_1_QUANTITY, 0);
        verifyCartItem(result, product2, PRODUCT_2_QUANTITY, 1);
    }

    private void addProductToCart(User user, Product product, Integer quantity) throws IOException, Exception {
        String cartUpdateJson = json(new CartUpdateDto(user.getId(), product.getId(), quantity));

        String cartUrl = String.format(CART_URL, user.getId());

        mockMvc.perform(post(cartUrl)
            .contentType(contentType)
            .content(cartUpdateJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.type", is(ResponseType.INFO.toString())))
            .andExpect(jsonPath("$.message", is(response.getMessage("Success.cart.updated"))));
    }

    private void verifyCartItem(ResultActions result, Product product, int quantity, int cartItemIndex) throws Exception {
        result.andExpect(jsonPath(String.format("$.cartItems[%d].quantity", cartItemIndex), is(quantity)))
            .andExpect(jsonPath(String.format("$.cartItems[%d].salesTax", cartItemIndex), is(product.calculateSalesTax(quantity))))
            .andExpect(jsonPath(String.format("$.cartItems[%d].priceBeforeTax", cartItemIndex), is(product.calculatePrice(quantity))))
            .andExpect(jsonPath(String.format("$.cartItems[%d].totalPrice", cartItemIndex), is(product.calculateTotalPrice(quantity))));
    }

    @Test
    public void test_clearCart() throws Exception {
        final int PRODUCT_1_QUANTITY = 5;
        final int PRODUCT_2_QUANTITY = 10;

        addProductToCart(user, product1, PRODUCT_1_QUANTITY);
        addProductToCart(user, product2, PRODUCT_2_QUANTITY);

        String cartUrl = String.format(CART_URL, user.getId());

        mockMvc.perform(delete(cartUrl)
            .contentType(contentType))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.type", is(ResponseType.INFO.toString())))
            .andExpect(jsonPath("$.message", is(response.getMessage("Success.cart.cleared"))));

        verifyEmptyCart(user.getId());
    }

    @Test
    public void test_deleteSpecificProductFromCart() throws Exception {
        final int PRODUCT_1_QUANTITY = 5;
        final int PRODUCT_2_QUANTITY = 10;

        addProductToCart(user, product1, PRODUCT_1_QUANTITY);
        addProductToCart(user, product2, PRODUCT_2_QUANTITY);

        String cartProductUrl = String.format(CART_URL, user.getId()) + "/" + product1.getId();

        mockMvc.perform(delete(cartProductUrl)
            .contentType(contentType))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.type", is(ResponseType.INFO.toString())))
            .andExpect(jsonPath("$.message", is(response.getMessage("Success.cart.product.removed"))));

        // verify that after removing product 1, only product 2 remains in the cart
        verifyCartHasOneProduct(product2, PRODUCT_2_QUANTITY);
    }

    private void verifyCartHasOneProduct(Product product, int quantity) throws Exception {
        ResultActions result = mockMvc
            .perform(get(String.format(CART_URL, user.getId())))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$.userId", is(user.getId().intValue())))
            .andExpect(jsonPath("$.totalPriceBeforeTax", is(product.calculatePrice(quantity))))
            .andExpect(jsonPath("$.totalSalesTax", is(product.calculateSalesTax(quantity))))
            .andExpect(jsonPath("$.grandTotal", is(product.calculateTotalPrice(quantity))))
            .andExpect(jsonPath("$.cartItems", hasSize(1)));
        verifyCartItem(result, product, quantity, 0);
    }

    @Test
    public void addToCart_WithZeroQuantityRemovesProduct() throws Exception {
        final int PRODUCT_1_QUANTITY = 5;
        final int PRODUCT_2_QUANTITY = 10;

        addProductToCart(user, product1, PRODUCT_1_QUANTITY);
        addProductToCart(user, product2, PRODUCT_2_QUANTITY);

        // update quantity of product2 to zero
        String cartUpdateJson = json(new CartUpdateDto(user.getId(), product2.getId(), 0));
        String cartUrl = String.format(CART_URL, user.getId());
        mockMvc.perform(post(cartUrl)
            .contentType(contentType)
            .content(cartUpdateJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.type", is(ResponseType.INFO.toString())))
            .andExpect(jsonPath("$.message", is(response.getMessage("Success.cart.updated"))));

        // verify that after setting quantity to zero for product 2, only product 1 remains in the cart
        verifyCartHasOneProduct(product1, PRODUCT_1_QUANTITY);
    }

}
