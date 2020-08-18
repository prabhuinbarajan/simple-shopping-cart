package com.bhn.shoppingcart.repository;

import com.bhn.shoppingcart.model.entity.Cart;
import com.bhn.shoppingcart.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    public Optional<Cart> findBySessionIdAndUserId(String sessionId, String userId);
    public Optional<Cart> findByUserId(String userId);

}
