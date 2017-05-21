package com.retail.store.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "product_category")
public class ProductCategory implements Serializable {

    private static final long serialVersionUID = -1349138690239364333L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "NotNull.productcategory.category")
    @Size(min = 1, max = 1, message = "Size.productcategory.category")
    private String category;

    @DecimalMin(value = "0.0", message = "DecimalMin.productcategory.salestaxpercentage")
    @DecimalMax(value = "100.0", message = "DecimalMax.productcategory.salestaxpercentage")
    private Double salesTaxPercentage;

    public ProductCategory() {
    }

    public ProductCategory(Long id, String category, Double salesTaxPercentage) {
        super();
        this.id = id;
        this.category = category;
        this.salesTaxPercentage = salesTaxPercentage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getSalesTaxPercentage() {
        return salesTaxPercentage;
    }

    public void setSalesTaxPercentage(Double salesTaxPercentage) {
        this.salesTaxPercentage = salesTaxPercentage;
    }

}
