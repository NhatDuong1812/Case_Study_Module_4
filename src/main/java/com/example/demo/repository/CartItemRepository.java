package com.example.demo.repository;

import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Iterable<CartItem> findAllByCart(Cart cart);
    CartItem getByCartIsAndProductsIs(Cart cart, Products product);
}
