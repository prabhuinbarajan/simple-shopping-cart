package com.bhn.shoppingcart.repository;

import com.bhn.shoppingcart.model.entity.Item;
import com.bhn.shoppingcart.model.entity.ItemPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, ItemPk> {

}
