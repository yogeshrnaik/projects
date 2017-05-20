package com.retail.store.dto;

import org.springframework.stereotype.Component;

import com.retail.store.model.Cart;
import com.retail.store.model.CartItem;

@Component
public class CartDtoBuilder {

    public CartDto buildCartDto(long userId, Cart cart) {
        CartDto cartDto = new CartDto(userId);
        cartDto.setTotalPrice(cart.getTotalPrice());
        cartDto.setTotalSalesTax(cart.getTotalSalesTax());
        cartDto.setGrandTotal(cart.getGrandTotal());

        for (CartItem item : cart.getCartItems()) {
            cartDto.getCartItems().add(
                new CartItemDto(item.getId(), item.getProduct(), item.getQuantity(), item.getSalesTax(), item.getPrice()));
        }
        return cartDto;
    }
}
