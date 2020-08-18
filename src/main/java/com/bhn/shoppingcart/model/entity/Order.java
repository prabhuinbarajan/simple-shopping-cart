package com.bhn.shoppingcart.model.entity;

import com.bhn.shoppingcart.model.types.OrderType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "created_date", columnDefinition = "datetime", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Valid
    @JsonManagedReference
    @OneToMany(mappedBy = "pk.order", fetch = FetchType.EAGER)
    private Set<Item> items = new HashSet<>();


    @Enumerated(EnumType.STRING)
    @Column(name = "order_type")
    private OrderType orderType;

    @Enumerated(EnumType.STRING)
    @Column(name = "channel")
    private com.bhn.shoppingcart.model.types.Channel channel;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private com.bhn.shoppingcart.model.types.OrderStatus status;


    //@OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "cart_id", referencedColumnName = "id")
    @OneToOne(mappedBy = "order")
    private Cart cart;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Objects.equals(getId(), order.getId()) &&
                Objects.equals(getCreatedDate(), order.getCreatedDate()) &&
                getOrderType()== order.getOrderType() &&
                getChannel() == order.getChannel() &&
                getStatus() == order.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCreatedDate());
    }
}
