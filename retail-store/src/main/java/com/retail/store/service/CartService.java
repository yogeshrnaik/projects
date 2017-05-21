package com.retail.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.retail.store.dto.CartDto;
import com.retail.store.dto.CartDtoBuilder;
import com.retail.store.dto.CartItemDto;
import com.retail.store.dto.CartUpdateDto;
import com.retail.store.model.Cart;
import com.retail.store.model.CartItem;
import com.retail.store.model.Product;
import com.retail.store.model.User;
import com.retail.store.repository.CartRepository;
import com.retail.store.repository.ProductRepository;
import com.retail.store.repository.UserRepository;

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

    public void updateCart(CartUpdateDto cartUpdateDto) {
        Product product = productService.getProduct(cartUpdateDto.getProductId());

        Cart cart = getCart(cartUpdateDto.getUserId());
        cart.addItem(product, cartUpdateDto.getQuantity());

        cartRepo.save(cart);
    }

    public Cart getCart(Long userId) {
        User user = userService.getUser(userId);
        return getCart(user);
    }

    public Cart getCart(User user) {
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            user.setCart(cart);
        }
        return cart;
    }

    public CartDto getCartByUserId(Long userId) {
        User user = userService.getUser(userId);
        Cart cart = user.getCart();
        if (cart == null) {
            return new CartDto(userId);
        }
        return cartDtoBuilder.buildCartDto(userId, cart);
    }
}
