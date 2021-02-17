package com.example.demo.service.cartItem;

import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Products;
import com.example.demo.service.IService;

import java.util.List;

public interface ICartItemService extends IService<CartItem>{
    Iterable<CartItem> findAllByCart(Cart cart);
    CartItem getByCartIsAndProductIs(Cart cart, Products product);
    List<CartItem> findBycarts(Cart cart);

}
