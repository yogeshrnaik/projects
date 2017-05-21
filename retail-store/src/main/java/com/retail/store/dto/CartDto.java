package com.retail.store.dto;

import java.util.LinkedHashSet;
import java.util.Set;

public class CartDto {

    private Long userId;

    private Set<CartItemDto> cartItems;

    private Double totalPriceBeforeTax;

    private Double totalSalesTax;

    private Double grandTotal;

    public CartDto(Long userId) {
        this(userId, new LinkedHashSet<>(), 0.0, 0.0);
    }

    public CartDto(Long userId, Set<CartItemDto> cartItems, Double totalPriceBeforeTax, Double totalSalesTax) {
        this.userId = userId;
        this.cartItems = cartItems;
        this.totalPriceBeforeTax = totalPriceBeforeTax;
        this.totalSalesTax = totalSalesTax;
        grandTotal = totalPriceBeforeTax + totalSalesTax;
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

    public Double getTotalPriceBeforeTax() {
        return totalPriceBeforeTax;
    }

    public void setTotalPriceBeforeTax(Double totalPrice) {
        totalPriceBeforeTax = totalPrice;
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
