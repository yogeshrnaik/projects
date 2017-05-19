package com.retail.store.model;

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

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems;

    private Double totalPrice;

    private Double totalSalesTax;

    private Double grandTotal;

    public Cart() {
    }

    public Cart(User user, Set<CartItem> cartItems) {
        this.user = user;
        this.cartItems = cartItems;
        totalPrice = 0.0;
        totalSalesTax = 0.0;
        grandTotal = 0.0;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Double getTotal() {
        return totalPrice;
    }

    public void setTotal(Double total) {
        totalPrice = total;
    }

    public Double getTotalSalesTax() {
        return totalSalesTax;
    }

    public void setTotalSalesTax(Double totalSalesTax) {
        this.totalSalesTax = totalSalesTax;
    }

    public void addItem(CartItem cartItem) {
        removeExistingItemIfPresent(cartItem);

        getCartItems().add(cartItem);

        totalSalesTax += cartItem.getSalesTax();
        totalPrice += cartItem.getPrice();
        grandTotal += cartItem.getTotalPrice();
    }

    private void removeExistingItemIfPresent(CartItem cartItem) {
        Iterator<CartItem> itr = getCartItems().iterator();
        while (itr.hasNext()) {
            CartItem item = itr.next();
            if (item.getProduct().getId().equals(cartItem.getProduct().getId())) {
                itr.remove();

                totalSalesTax -= item.getSalesTax();
                totalPrice -= item.getPrice();
                grandTotal -= item.getTotalPrice();
                break;
            }
        }
    }

}
