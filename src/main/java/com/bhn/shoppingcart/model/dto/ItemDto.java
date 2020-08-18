package com.bhn.shoppingcart.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private ProductDto product;
    private Integer quantity;

    @JsonProperty("total_price")
    public BigDecimal getTotalPrice() {
        return this.getProduct().getPrice().multiply(BigDecimal.valueOf(this.getQuantity()));
    }

}
