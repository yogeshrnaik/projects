package com.retail.store.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartUpdateDto {

    @NotNull(message = "NotNull.cartupdatedto.userid")
    private Long userId;

    @NotNull(message = "NotNull.cartupdatedto.productid")
    private Long productId;

    @NotNull(message = "NotNull.cartupdatedto.quantity")
    @Min(value = 0, message = "Min.cartupdatedto.quantity")
    private Integer quantity;

    public CartUpdateDto() {
    }

    public CartUpdateDto(Long userId, Long productId, Integer quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
