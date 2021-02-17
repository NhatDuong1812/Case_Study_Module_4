package com.example.demo.service.cart;

import com.example.demo.exeption.NotFoundException;
import com.example.demo.model.AppUser;
import com.example.demo.model.Cart;
import com.example.demo.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CartService implements ICartService{
    @Autowired
    private CartRepository cartRepository;
    @Override
    public Iterable<Cart> findAll() {
        return null;
    }

    @Override
    public Optional<Cart> findById(Long id) throws NotFoundException {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart findByAppUser(AppUser appUser) {
        return cartRepository.findByAppUser(appUser);
    }
}
