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

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems;

    private Double totalPrice;

    private Double totalSalesTax;

    private Double grandTotal;

    public Cart() {
        cartItems = new HashSet<>();
        totalPrice = 0.0;
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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public void addItem(CartItem cartItem) {
        boolean alreadyExists = updateExistingItemIfPresent(cartItem);

        if (!alreadyExists) {
            getCartItems().add(cartItem);

            incrementTotals(cartItem);
        }
    }

    private boolean updateExistingItemIfPresent(CartItem newItem) {
        Iterator<CartItem> itr = getCartItems().iterator();
        while (itr.hasNext()) {
            CartItem existingItem = itr.next();
            if (existingItem.getProduct().getId().equals(newItem.getProduct().getId())) {
                decrementTotals(existingItem);
                existingItem.updateQuantity(newItem.getQuantity());
                incrementTotals(existingItem);
                return true;
            }
        }
        return false;
    }

    private void decrementTotals(CartItem cartItem) {
        totalSalesTax -= cartItem.getSalesTax();
        totalPrice -= cartItem.getPrice();
        grandTotal -= cartItem.getTotalPrice();
    }

    private void incrementTotals(CartItem cartItem) {
        totalSalesTax += cartItem.getSalesTax();
        totalPrice += cartItem.getPrice();
        grandTotal += cartItem.getTotalPrice();
    }

}
