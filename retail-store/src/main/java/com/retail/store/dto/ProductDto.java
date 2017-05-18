package com.retail.store.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductDto {

    @NotNull(message = "NotNull.product.name")
    @Size(min = 2, max = 100, message = "Size.product.name")
    private String name;

    @DecimalMin(value = "1.0", message = "DecimalMin.product.unitprice")
    private Double unitPrice;

    @NotNull(message = "NotNull.product.category")
    private Long categoryId;

    public ProductDto() {
    }

    public ProductDto(String name, Double unitPrice, Long categoryId) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.categoryId = categoryId;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

}
