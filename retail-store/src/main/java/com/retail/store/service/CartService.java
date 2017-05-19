package com.retail.store.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.retail.store.dto.ProductDto;
import com.retail.store.exception.NotFoundException;
import com.retail.store.model.Cart;
import com.retail.store.model.CartItem;
import com.retail.store.model.Product;
import com.retail.store.model.ProductCategory;
import com.retail.store.model.User;
import com.retail.store.repository.CartRepository;
import com.retail.store.repository.ProductRepository;
import com.retail.store.repository.UserRepository;

@Component
public class CartService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private ProductRepository productRepo;

    public void addProduct(long userId, long productId, int quantity) {
        User user = userRepo.getOne(userId);
        Cart cart = getCart(user);

        CartItem cartItem = createCartItem(productId, quantity, cart);
        cart.addItem(cartItem);

        cartRepo.saveAndFlush(cart);
    }

    private Cart getCart(User user) {
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart(user, new HashSet<>());
            user.setCart(cart);
        }
        return cart;
    }

    private CartItem createCartItem(long productId, int quantity, Cart cart) {
        Product product = productRepo.getOne(productId);
        return new CartItem(null, product, quantity, cart);
    }
}
