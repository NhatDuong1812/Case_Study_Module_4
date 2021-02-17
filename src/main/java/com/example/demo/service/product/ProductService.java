package com.example.demo.service.product;

import com.example.demo.model.*;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.cartItem.ICartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class ProductService implements IProductService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ICartItemService cartItemService;
    @Override
    public Page<Products> findAllByNameContaining(String name, Pageable pageable) {
        return productRepository.findAllByNameContaining(name,pageable);
    }

    @Override
    public Iterable<Products> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Page<Products> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Products> findAllByTradeMark(TradeMark tradeMark, Pageable pageable) {
        return productRepository.findAllByTradeMark(tradeMark,pageable);
    }

    @Override
    public Page<Products> findAllByCategory(Category category, Pageable pageable) {
        return productRepository.findAllByCategory(category,pageable);
    }

    @Override
    public Page<Products> findAllByNameContainingAndCategory(String name, Category category, Pageable pageable) {
        return productRepository.findAllByNameContainingAndCategory(name,category,pageable);
    }

    @Override
    public Page<Products> findAllByNameContainingAndTradeMark(String name, TradeMark tradeMark, Pageable pageable) {
        return productRepository.findAllByNameContainingAndTradeMark(name,tradeMark,pageable);
    }

    @Override
    public Page<Products> findAllByCategoryAndTradeMark(Category category, TradeMark tradeMark, Pageable pageable) {
        return productRepository.findAllByCategoryAndTradeMark(category,tradeMark,pageable);
    }

    @Override
    public Page<Products> findAllByNameContainingAndTradeMarkAndCategory(String name, Category category, TradeMark tradeMark, Pageable pageable) {
        return productRepository.findAllByNameContainingAndTradeMarkAndCategory(name,category,tradeMark,pageable);
    }

    @Override
    public Iterable<Products> findAllBy8Day() {
        return productRepository.findAllBy8Day();
    }

    @Override
    public Iterable<Products> findAllByTradeMark(TradeMark tradeMark) {
        return productRepository.findAllByTradeMark(tradeMark);
    }

    @Override
    public Iterable<Products> findAllByPriceBetween(Double min, Double max) {
        return productRepository.findAllByPriceBetween(min,max);
    }

    @Override
    public List<Products> findAllByCart(Cart cart) {
        Iterable<CartItem> cartItems = cartItemService.findAllByCart(cart);
        List<Products> productList = new ArrayList<>();
        for (CartItem item:cartItems
        ) {
            productList.add(item.getProducts());
        }
        return productList;
    }

    @Override
    public Optional<Products> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Products save(Products products) {
        return productRepository.save(products);
    }

}
