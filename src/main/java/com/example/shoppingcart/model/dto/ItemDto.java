package com.example.shoppingcart.model.dto;

import java.math.BigDecimal;

public class ItemDto {
    private ProductDto product;
    private Integer quantity;

    public ItemDto() {
    }

    public ItemDto(ProductDto product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return this.getProduct().getPrice().multiply(BigDecimal.valueOf(this.getQuantity()));
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
