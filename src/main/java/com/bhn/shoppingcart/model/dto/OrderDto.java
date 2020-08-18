package com.bhn.shoppingcart.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class OrderDto {

    private Long id;
    private LocalDateTime dateCreated;
    private Set<ItemDto> items = new HashSet<>();

    public OrderDto() {
    }

    public OrderDto(Long id, LocalDateTime dateCreated, Set<ItemDto> items) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.items = items;
    }

    @JsonProperty("total_order_price")
    public BigDecimal getTotalOrderPrice() {
        return this.getItems()
                .stream()
                .map(ItemDto::getTotalPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    @JsonProperty("number_of_products")
    public Integer getNumberOfProducts() {
        return this.items
                .stream()
                .mapToInt(ItemDto::getQuantity)
                .reduce(0, Integer::sum);
    }


}
