package com.retail.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.retail.store.dto.CartDto;
import com.retail.store.dto.CartDtoBuilder;
import com.retail.store.dto.CartUpdateDto;
import com.retail.store.model.Cart;
import com.retail.store.model.Product;
import com.retail.store.model.User;
import com.retail.store.repository.CartRepository;

@Component
public class CartService {

    @Autowired
    private UserService userService;

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartDtoBuilder cartDtoBuilder;

    /**
     * Updates the Cart of a particular user.
     *
     * @param cartUpdateDto
     */
    public void updateCart(CartUpdateDto cartUpdateDto) {
        Product product = productService.getProduct(cartUpdateDto.getProductId());

        Cart cart = getCart(cartUpdateDto.getUserId());
        cart.addItem(product, cartUpdateDto.getQuantity());

        cartRepo.save(cart);
    }

    /**
     * Clears all items from the cart of a particular user.
     *
     * @param userId
     */
    public void clearCart(Long userId) {
        User user = userService.getUser(userId);
        Cart cart = user.getCart();
        if (cart != null) {
            cart.clear();
            cartRepo.save(cart);
        }
    }

    /**
     * Gets cart of a particular user
     *
     * @param userId
     * @return cartDto
     */
    public CartDto getCartByUserId(Long userId) {
        User user = userService.getUser(userId);
        Cart cart = user.getCart();
        if (cart == null) {
            return new CartDto(userId);
        }
        return cartDtoBuilder.buildCartDto(userId, cart);
    }

    private Cart getCart(Long userId) {
        User user = userService.getUser(userId);
        return getCart(user);
    }

    private Cart getCart(User user) {
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            user.setCart(cart);
        }
        return cart;
    }
}
