package com.retail.store.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cart_items")
public class CartItem implements Serializable {

    private static final long serialVersionUID = -5454681550675014977L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Cart cart;

    @NotNull
    private int quantity;

    private double salesTax;

    private double priceBeforeTax;

    private double totalPrice;

    public CartItem() {
    }

    public CartItem(Product product, int quantity, Cart cart) {
        this(null, product, quantity, cart);
    }

    public CartItem(Long id, Product product, int quantity, Cart cart) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.cart = cart;

        updateQuantity(quantity);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(Double salesTax) {
        this.salesTax = salesTax;
    }

    public Double getPriceBeforeTax() {
        return priceBeforeTax;
    }

    public void setPriceBeforeTax(Double price) {
        priceBeforeTax = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setSalesTax(double salesTax) {
        this.salesTax = salesTax;
    }

    public void updateQuantity(int quantity) {
        setQuantity(quantity);
        priceBeforeTax = product.calculatePrice(quantity);
        salesTax = product.calculateSalesTax(quantity);
        totalPrice = priceBeforeTax + salesTax;
    }
}
