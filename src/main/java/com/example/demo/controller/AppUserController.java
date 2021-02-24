package com.example.demo.controller;

import com.example.demo.model.AppRole;
import com.example.demo.model.AppUser;
import com.example.demo.model.Cart;
import com.example.demo.service.appUser.IAppUserService;
import com.example.demo.service.cart.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppUserController {
    @Autowired
    private IAppUserService appUserService;
    @Autowired
    private ICartService cartService;
    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView modelAndView=new ModelAndView("user/login");
        return modelAndView;
    }
//    @RequestMapping(value = { "/login" })
//    public String login(@RequestParam(required=false) String message, final Model model) {
//        if (message != null && !message.isEmpty()) {
//            if (message.equals("timeout")) {
//                model.addAttribute("message", "Time out");
//            }
//            if (message.equals("max_session")) {
//                model.addAttribute("message", "This accout has been login from another device!");
//            }
//            if (message.equals("logout")) {
//                model.addAttribute("message", "Logout!");
//            }
//            if (message.equals("error")) {
//                model.addAttribute("message", "Login Failed!");
//            }
//        }
//        return "redirect:/login";
//    }
        @GetMapping(value = {"/register","/shop/register"})
    public ModelAndView createUser(){
        ModelAndView modelAndView= new ModelAndView("user/create");
        modelAndView.addObject("user",new AppUser());
        return modelAndView;
    }
    @PostMapping(value = {"/register","/shop/register"})
    public ModelAndView createAppUser(@ModelAttribute AppUser user){
        ModelAndView modelAndView= new ModelAndView("user/create");
        AppRole appRole= new AppRole();
        appRole.setId((long) 2);
        appRole.setName("ROLE_USER");
        user.setRole(appRole);
        Cart cart= new Cart();
        cartService.save(cart);
        appUserService.save(user);
        modelAndView.addObject("user", new AppUser());
        return modelAndView;
    }
   }



