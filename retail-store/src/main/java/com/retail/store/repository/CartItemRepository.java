package com.retail.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.retail.store.model.Cart;
import com.retail.store.model.CartItem;
import com.retail.store.model.Product;
import com.retail.store.model.User;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
