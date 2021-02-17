package com.example.demo.controller;

import com.example.demo.exeption.NotFoundException;
import com.example.demo.model.AppUser;
import com.example.demo.model.Cart;
import com.example.demo.model.CartItem;
import com.example.demo.model.Products;
import com.example.demo.service.appUser.IAppUserService;
import com.example.demo.service.cart.ICartService;
import com.example.demo.service.cartItem.ICartItemService;
import com.example.demo.service.product.IProductService;
import lombok.experimental.PackagePrivate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    private IAppUserService appUserService;

    @Autowired
    private ICartService cartService;

    @Autowired
    private IProductService productService;

    @Autowired
    private ICartItemService cartItemService;

    @ModelAttribute()
    public AppUser currentUser(){
        return appUserService.getCurrentUser();
    }

    @ModelAttribute("currentCart")
    public Cart currentCart() {
        Cart cart = cartService.findByAppUser(currentUser());
        return cart;
    }

    @GetMapping("/cart/add/{id}")
    public ResponseEntity<Iterable<Products>> postCart(@PathVariable Long id) throws NotFoundException {
        Products product = productService.findById(id).get();
        List<Products> allProductInCurrentCart = productService.findAllByCart(currentCart());
        Cart cart = null;
        if (currentCart() == null){
            cart = new Cart();
        }else {
            cart = currentCart();
        }
        boolean isContains = allProductInCurrentCart.contains(product);
        if (isContains){
            CartItem currentItems = cartItemService.getByCartIsAndProductIs(currentCart(), product);
            int currentQuantity = currentItems.getQuantity();
            currentQuantity++;
            currentItems.setQuantity(currentQuantity);
            cartItemService.save(currentItems);
        }else  {
            if (currentCart() == null){
                cart = new Cart();
                cart.setAppUser(currentUser());
                cartService.save(cart);
            }
            CartItem cartItem = new CartItem();
            cartItem.setQuantity(1);
            cartItem.setProducts(product);
            cartItem.setCart(cart);
            cartItemService.save(cartItem);
        }
        Iterable<Products> list= productService.findAllByCart(currentCart());
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @GetMapping("/cart/count")
    public ResponseEntity coutPr(@PathVariable Long id) throws NotFoundException {
        List<CartItem> cartItems = cartItemService.findBycarts(currentCart());
        return new ResponseEntity(cartItems,HttpStatus.OK);
    }

    @GetMapping("/total")
    public ResponseEntity total() {
        Double total  = 0.0;
        List<CartItem> cartItems = cartItemService.findBycarts(currentCart());
        for (CartItem cartItem : cartItems){
            total += cartItem.getQuantity() * cartItem.getProducts().getPrice();
        }
        return new ResponseEntity(total,HttpStatus.OK);
    }

    @GetMapping("/quantity")
    public ResponseEntity quantity(){
        List<CartItem> cartItems = cartItemService.findBycarts(currentCart());
        List<Integer> list = new ArrayList();
        for (CartItem cartItem : cartItems){
            list.add(cartItem.getQuantity());
        }
        return new ResponseEntity(list,HttpStatus.OK);
    }

}
