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
import javax.validation.constraints.Size;

@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 8165151366863682663L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "NotNull.product.name")
    @Size(min = 2, max = 100, message = "Size.product.name")
    private String name;

    private Double unitPrice;

    @ManyToOne
    private ProductCategory category;

    public Product() {
    }

    public Product(Long id, String name, Double unitPrice, ProductCategory category) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public double calculateSalesTax(int quantity) {
        return calculatePrice(quantity) * getCategory().getSalesTaxPercentage() / 100;
    }

    public double calculatePrice(int quantity) {
        return getUnitPrice() * quantity;
    }

    public double calculateTotalPrice(int quantity) {
        return calculatePrice(quantity) + calculateSalesTax(quantity);
    }
}
