package com.retail.store.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.retail.store.dto.CartUpdateDto;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems;

    private Double totalPriceBeforeTax;

    private Double totalSalesTax;

    private Double grandTotal;

    public Cart() {
        cartItems = new HashSet<>();
        totalPriceBeforeTax = 0.0;
        totalSalesTax = 0.0;
        grandTotal = 0.0;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Double getTotalSalesTax() {
        return totalSalesTax;
    }

    public void setTotalSalesTax(Double totalSalesTax) {
        this.totalSalesTax = totalSalesTax;
    }

    public Double getTotalPriceBeforeTax() {
        return totalPriceBeforeTax;
    }

    public void setTotalPriceBeforeTax(Double totalPrice) {
        totalPriceBeforeTax = totalPrice;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

    /**
     * Add item in the cart only if not already exists in cart and its quantity is > 0. <br>
     * If already exists and new quantity is zero, then that item is removed from cart.
     *
     * @param product
     * @param quantity
     */
    public void addItem(Product product, int quantity) {
        CartItem newItem = new CartItem(product, quantity, this);
        boolean alreadyExists = updateExistingItemIfPresent(newItem);
        if (!alreadyExists && quantity > 0) {
            getCartItems().add(newItem);
            incrementTotals(newItem);
        }
    }

    private boolean updateExistingItemIfPresent(CartItem newItem) {
        Iterator<CartItem> itr = getCartItems().iterator();

        while (itr.hasNext()) {
            CartItem existingItem = itr.next();
            if (existingItem.getProduct().getId().equals(newItem.getProduct().getId())) {
                decrementTotals(existingItem);

                if (newItem.getQuantity() <= 0) {
                    itr.remove();
                } else {
                    existingItem.updateQuantity(newItem.getQuantity());
                    incrementTotals(existingItem);
                }
                return true;
            }
        }
        return false;
    }

    private void decrementTotals(CartItem cartItem) {
        totalSalesTax -= cartItem.getSalesTax();
        totalPriceBeforeTax -= cartItem.getPriceBeforeTax();
        grandTotal -= cartItem.getTotalPrice();
    }

    private void incrementTotals(CartItem cartItem) {
        totalSalesTax += cartItem.getSalesTax();
        totalPriceBeforeTax += cartItem.getPriceBeforeTax();
        grandTotal += cartItem.getTotalPrice();
    }

}
