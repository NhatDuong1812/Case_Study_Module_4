package com.example.demo.repository;

import com.example.demo.model.AppUser;
import com.example.demo.model.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Long> {
    Cart findByAppUser(AppUser appUser);
}
