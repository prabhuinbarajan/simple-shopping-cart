package com.example.shoppingcart.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Items")
public class Item implements Serializable {

    @EmbeddedId
    @JsonIgnore
    private ItemPk pk;

    @NotNull
    @Column(name = "ItemQuantity", nullable = false)
    private Integer quantity;

    public Item() {
    }

    public Item(Order order, Product product, Integer quantity) {
        pk = new ItemPk();
        pk.setOrder(order);
        pk.setProduct(product);
        this.quantity = quantity;
    }

    public ItemPk getPk() {
        return pk;
    }

    public void setPk(ItemPk pk) {
        this.pk = pk;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Objects.equals(getPk(), item.getPk()) &&
                Objects.equals(getQuantity(), item.getQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPk(), getQuantity());
    }
}
