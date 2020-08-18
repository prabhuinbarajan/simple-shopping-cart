package com.bhn.shoppingcart.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    public Cart(String sessionId,String userId) {
        this.userId = userId;
        this.sessionId = sessionId;
    }

    @Column(name = "session_id", nullable = false)
    private String sessionId;

    @Column(name = "created_date", columnDefinition = "datetime", nullable = false)
    private LocalDateTime createdDate;

    @Valid
    @JsonManagedReference
    //@OneToOne(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@OneToOne(mappedBy = "cart")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart)) return false;
        Cart order = (Cart) o;
        return Objects.equals(getId(), order.getId()) &&
                Objects.equals(getCreatedDate(), order.getCreatedDate()) &&
                Objects.equals(getSessionId(), order.getSessionId()) &&
                Objects.equals(getUserId(), order.getUserId());

    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCreatedDate());
    }
}
