package com.example.shoppingcart.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    public BigDecimal getTotalOrderPrice() {
        return this.getItems()
                .stream()
                .map(ItemDto::getTotalPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public Integer getNumberOfProducts() {
        return this.items
                .stream()
                .mapToInt(ItemDto::getQuantity)
                .reduce(0, Integer::sum);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Set<ItemDto> getItems() {
        return items;
    }

    public void setItems(Set<ItemDto> items) {
        this.items = items;
    }
}
