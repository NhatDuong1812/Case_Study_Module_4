package com.example.demo.controller;

import com.example.demo.exeption.NotFoundException;
import com.example.demo.model.*;
import com.example.demo.service.IService;
import com.example.demo.service.appUser.IAppUserService;
import com.example.demo.service.cart.ICartService;
import com.example.demo.model.Category;
import com.example.demo.model.Comment;
import com.example.demo.model.Products;
import com.example.demo.model.TradeMark;
import com.example.demo.service.cartItem.ICartItemService;
import com.example.demo.service.category.ICategoryService;
import com.example.demo.service.comment.ICommentService;
import com.example.demo.service.product.IProductService;
import com.example.demo.service.trademark.ITrademarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    IProductService productService;

    @Autowired
    ICategoryService categoryService;

    @Autowired
    ITrademarkService trademarkService;

    @Autowired
    ICommentService commentService;

    @Autowired
    IAppUserService appUserService;

    @Autowired
    private ICartItemService cartItemService;

    @Autowired
    private ICartService cartService;

    @ModelAttribute("quantity")
    public List<Integer> quantity(){
        List<CartItem> cartItems = cartItemService.findBycarts(currentCart());
        List<Integer> list = new ArrayList();
        for (CartItem cartItem : cartItems){
            list.add(cartItem.getQuantity());
        }
        return list;
    }
    @ModelAttribute("count")
    public Integer coutPr() {
        List<CartItem> cartItems = cartItemService.findBycarts(currentCart());
        Integer count = cartItems.size();
        return count;
    }
    @ModelAttribute("total")
    public Double total() {
        Double total  = 0.0;
        List<CartItem> cartItems = cartItemService.findBycarts(currentCart());
        for (CartItem cartItem : cartItems){
            total += cartItem.getQuantity() * cartItem.getProducts().getPrice();
        }
        return total;
    }

    @ModelAttribute("user")
    public AppUser currentUser(){
        return appUserService.getCurrentUser();
    }

    @ModelAttribute("currentCart")
    public Cart currentCart() {
        Cart cart = cartService.findByAppUser(currentUser());
        return cart;
    }

    @ModelAttribute("cartItem")
    public Iterable<Products> cartItem(){
        Iterable<Products> products= productService.findAllByCart(currentCart());
        return products;
    }


    @ModelAttribute("category")
    public Iterable<Category> categories(@PageableDefault(size = 3) Pageable pageable) {
        return categoryService.findAll(pageable);
    }
    @ModelAttribute("categorys")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    @ModelAttribute("trademark")
    public Iterable<TradeMark> tradeMarks( ) {
        return trademarkService.findAll();
    }

    @RequestMapping("")
    public ModelAndView index(@PageableDefault(size = 7) Pageable pageable) throws NotFoundException {
        ModelAndView modelAndView = new ModelAndView("shop/index");
        Page<TradeMark> tradeMarks = trademarkService.findAll(pageable);
        TradeMark tradeMark1 = trademarkService.findById(1L).get();
        List<Category> categories = categoryService.findNewCategory();
        Category cat1 = categoryService.findById(categories.get(0).getId()).get();
        Category cat2 = categoryService.findById(categories.get(1).getId()).get();
        Category cat3 = categoryService.findById(categories.get(2).getId()).get();
        Page<Products> categories1 = productService.findAllByCategory(cat1,pageable);
        Page<Products> categories2 = productService.findAllByCategory(cat2,pageable);
        Page<Products> categories3 = productService.findAllByCategory(cat3,pageable);
        Iterable<Products> tradeProduct = productService.findAllByTradeMark(tradeMark1);
        Iterable<Products> newProduct = productService.findAllBy8Day();
        modelAndView.addObject("newProduct",newProduct);
        modelAndView.addObject("cat1",cat1);
        modelAndView.addObject("cat2",cat2);
        modelAndView.addObject("cat3",cat3);
        modelAndView.addObject("categories1",categories1);
        modelAndView.addObject("categories2",categories2);
        modelAndView.addObject("categories3",categories3);
        modelAndView.addObject("tradeMarks",tradeMarks);
        modelAndView.addObject("tradeProduct",tradeProduct);
        return modelAndView;
    }

    @RequestMapping("/details/{id}")
    public ModelAndView getDetails(@PathVariable Long id, @PageableDefault(size = 4) Pageable pageable) throws NotFoundException {
        ModelAndView modelAndView = new ModelAndView("shop/details");
        Products product = productService.findById(id).get();
        Category category = product.getCategory();
        Iterable<Comment> comments = commentService.findAllByProduct(product);
        Iterable<Products> products = productService.findAllByCategory(category,pageable);
        Integer cmtSum = commentService.countAllByProduct(product);
        modelAndView.addObject("product",product);
        modelAndView.addObject("cmtSum",cmtSum);
        modelAndView.addObject("comments",comments);
        modelAndView.addObject("products",products);
        return modelAndView;
    }

    @RequestMapping("/test")
    public ModelAndView getTest() {
        ModelAndView modelAndView = new ModelAndView("shop/test");
        return modelAndView;
    }

    @RequestMapping("/getPrTrademark/{id}")
    public ResponseEntity<Iterable<Products>> getProductsWithApi(@PathVariable Long id) throws NotFoundException {
        TradeMark tradeMark = trademarkService.findById(id).get();
        Iterable<Products> products = productService.findAllByTradeMark(tradeMark);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/store")
    public ModelAndView getStore(@RequestParam(value ="tradeMark",required = false,defaultValue = "") String tradeMark,@RequestParam(value ="category",required = false,defaultValue = "") String category,@PageableDefault(size = 15) Pageable pageable) throws NotFoundException {
        Page<Products> products;
        Long cat_id = null;
        Long trade_id = null;
        Category category1 = new Category();
        TradeMark tradeMark1 = new TradeMark();
        if (!category.equals("")) {
            cat_id = Long.parseLong(category);
            category1 = categoryService.findById(cat_id).get();
        }
        if(!tradeMark.equals("")) {
            trade_id = Long.parseLong(tradeMark);
            tradeMark1 = trademarkService.findById(trade_id).get();
        }
        if(!category.equals("") && tradeMark.equals("")){
            products = productService.findAllByCategory(category1, pageable);
        }else if (category.equals("") && !tradeMark.equals("")){
            products = productService.findAllByTradeMark(tradeMark1,pageable);
        }else if (!category.equals("") && !tradeMark.equals("")){
            products = productService.findAllByCategoryAndTradeMark(category1,tradeMark1,pageable);
        }else {
            products =productService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("shop/store");
        modelAndView.addObject("listproduct",products);
        return modelAndView;
    }

    @GetMapping("/price")
    public ResponseEntity<Iterable<Products>> getAllProByPrice(@RequestParam(value = "min-price",required = false,defaultValue = "") String min,@RequestParam(value = "max-price",required = false,defaultValue = "") String max){
        Double maxPrice = Double.parseDouble(max);
        Double minPrice = Double.parseDouble(min);
        Iterable<Products> allProductByPrice = productService.findAllByPriceBetween(minPrice,maxPrice);
        return new ResponseEntity<>(allProductByPrice,HttpStatus.OK);
    }

    @GetMapping("/register")
    public ModelAndView createUser(){
        ModelAndView modelAndView= new ModelAndView("user/create");
        modelAndView.addObject("user",new AppUser());
        return modelAndView;
    }
   @PostMapping("/register")
    public ModelAndView createAppUser(@ModelAttribute AppUser user){
        ModelAndView modelAndView= new ModelAndView("user/create");
        AppRole appRole= new AppRole();
        appRole.setId((long) 2);
        appRole.setName("ROLE_USER");
        modelAndView.addObject("user", new AppUser());
        return modelAndView;
    }

    @GetMapping("/checkout")
    public ModelAndView checkout() {
        ModelAndView modelAndView = new ModelAndView("/shop/checkout");
        return modelAndView;
    }
}
