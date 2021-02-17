package com.example.demo.service.product;

import com.example.demo.model.Cart;
import com.example.demo.model.Category;
import com.example.demo.model.Products;
import com.example.demo.model.TradeMark;
import com.example.demo.service.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService extends IService<Products> {
    Page<Products> findAllByNameContaining(String name, Pageable pageable);
    Page<Products> findAll(Pageable pageable);
    Page<Products> findAllByTradeMark(TradeMark tradeMark, Pageable pageable);
    Page<Products> findAllByCategory(Category category, Pageable pageable);
    Page<Products> findAllByNameContainingAndCategory(String name,Category category, Pageable pageable);
    Page<Products> findAllByNameContainingAndTradeMark(String name,TradeMark tradeMark, Pageable pageable);
    Page<Products> findAllByCategoryAndTradeMark(Category category, TradeMark tradeMark, Pageable pageable);
    Page<Products> findAllByNameContainingAndTradeMarkAndCategory(String name,Category category, TradeMark tradeMark, Pageable pageable);
    Iterable<Products> findAllBy8Day();
    Iterable<Products>findAllByTradeMark(TradeMark tradeMark);
    Iterable<Products> findAllByPriceBetween(Double min, Double max);
    List<Products> findAllByCart(Cart cart);
}
