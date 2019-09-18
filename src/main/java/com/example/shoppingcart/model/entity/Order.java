package com.example.shoppingcart.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderId", nullable = false)
    private Long id;

    @Column(name = "OrderCreationTime", columnDefinition = "datetime", nullable = false)
    private LocalDateTime creationTime;

    @Valid
    @JsonManagedReference
    @OneToMany(mappedBy = "pk.order", fetch = FetchType.EAGER)
    private Set<Item> items = new HashSet<>();

    public Order() {
    }

    public Order(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Objects.equals(getId(), order.getId()) &&
                Objects.equals(getCreationTime(), order.getCreationTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCreationTime());
    }
}
