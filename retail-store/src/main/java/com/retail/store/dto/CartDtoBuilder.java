package com.retail.store.dto;

import org.springframework.stereotype.Component;

import com.retail.store.model.Cart;
import com.retail.store.model.CartItem;

@Component
public class CartDtoBuilder {

    /**
     * Builds a CartDto object using information from a Cart entity.
     * 
     * @param userId
     * @param cart
     * @return cartDto
     */
    public CartDto buildCartDto(long userId, Cart cart) {
        CartDto cartDto = new CartDto(userId);
        cartDto.setTotalPriceBeforeTax(cart.getTotalPriceBeforeTax());
        cartDto.setTotalSalesTax(cart.getTotalSalesTax());
        cartDto.setGrandTotal(cart.getGrandTotal());

        for (CartItem item : cart.getCartItems()) {
            cartDto.getCartItems().add(
                new CartItemDto(item.getId(), item.getProduct(), item.getQuantity(), item.getSalesTax(), item.getPriceBeforeTax()));
        }
        return cartDto;
    }
}
