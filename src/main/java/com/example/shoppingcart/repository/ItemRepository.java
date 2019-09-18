package com.example.shoppingcart.repository;

import com.example.shoppingcart.model.entity.Item;
import com.example.shoppingcart.model.entity.ItemPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, ItemPk> {

}
