package com.groupfive.assignment.repository;

import com.groupfive.assignment.model.Cart;
import com.groupfive.assignment.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
