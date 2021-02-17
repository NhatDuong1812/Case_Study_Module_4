package com.example.demo.service.cart;

import com.example.demo.model.AppUser;
import com.example.demo.model.Cart;
import com.example.demo.service.IService;

public interface ICartService extends IService<Cart> {
    Cart findByAppUser(AppUser appUser);
}
