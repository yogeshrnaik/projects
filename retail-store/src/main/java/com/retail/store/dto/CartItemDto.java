package com.retail.store.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.retail.store.model.Product;

public class CartItemDto {

    private Long id;

    private Product product;

    private int quantity;

    private double salesTax;

    private double priceBeforeTax;

    private double totalPrice;

    public CartItemDto(Long id, Product product, int quantity, double salesTax, double price) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.salesTax = salesTax;
        this.priceBeforeTax = price;
        totalPrice = price + salesTax;
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

    public double getSalesTax() {
        return salesTax;
    }

    public void setSalesTax(double salesTax) {
        this.salesTax = salesTax;
    }

    public double getPriceBeforeTax() {
        return priceBeforeTax;
    }

    public void setPriceBeforeTax(double price) {
        this.priceBeforeTax = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

}
