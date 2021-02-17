package com.example.demo.controller;

import com.example.demo.model.AppRole;
import com.example.demo.model.AppUser;
import com.example.demo.model.Cart;
import com.example.demo.service.appUser.IAppUserService;
import com.example.demo.service.cart.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class AppUserController {
    @Autowired
    private IAppUserService appUserService;
    @Autowired
    private ICartService cartService;
    @GetMapping("/create")
    public ModelAndView createUser(){
        ModelAndView modelAndView= new ModelAndView("user/create");
        modelAndView.addObject("user",new AppUser());
        return modelAndView;
    }
   /* @PostMapping("/create")
    public ModelAndView createAppUser(@ModelAttribute AppUser user){
        ModelAndView modelAndView= new ModelAndView("user/create");
        AppRole appRole= new AppRole();
        appRole.setId((long) 2);
        appRole.setName("ROLE_USER");
        user.setRole(appRole);
        Cart cart= new Cart();
        cartService.save(cart);
        user.setCart(cart);
        appUserService.save(user);
        modelAndView.addObject("user", new AppUser());
        return modelAndView;
    }*/
   }



