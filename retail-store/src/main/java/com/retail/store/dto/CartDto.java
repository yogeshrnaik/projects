package com.retail.store.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import com.retail.store.model.CartItem;

public class CartDto {

    private Long userId;

    private Set<CartItemDto> cartItems;

    private Double totalPrice;

    private Double totalSalesTax;

    private Double grandTotal;

    public CartDto(Long userId) {
        this(userId, new HashSet<CartItemDto>(), 0.0, 0.0);
    }

    public CartDto(Long userId, Set<CartItemDto> cartItems, Double totalPrice, Double totalSalesTax) {
        this.userId = userId;
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
        this.totalSalesTax = totalSalesTax;
        grandTotal = totalPrice + totalSalesTax;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Set<CartItemDto> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItemDto> cartItems) {
        this.cartItems = cartItems;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTotalSalesTax() {
        return totalSalesTax;
    }

    public void setTotalSalesTax(Double totalSalesTax) {
        this.totalSalesTax = totalSalesTax;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

}
