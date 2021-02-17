package com.example.demo.controller;

import com.example.demo.exeption.NotFoundException;
import com.example.demo.model.Category;
import com.example.demo.model.Products;
import com.example.demo.model.TradeMark;
import com.example.demo.service.category.ICategoryService;
import com.example.demo.service.product.IProductService;
import com.example.demo.service.trademark.ITrademarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/admin/product")
public class ProductController {
    @Autowired
    IProductService productService;

    @Autowired
    ICategoryService categoryService;

    @Autowired
    ITrademarkService trademarkService;

    @Autowired
    private Environment environment;

    @ModelAttribute("category")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    @ModelAttribute("trademark")
    public Iterable<TradeMark> tradeMarks() {
        return trademarkService.findAll();
    }

    @GetMapping("")
    public ModelAndView getList(@RequestParam(value = "name",required = false,defaultValue = "") String name,@RequestParam(value = "category",required = false, defaultValue = "") String category,@RequestParam(value = "tradeMark",required = false,defaultValue = "") String tradeMark, @PageableDefault(size = 10) Pageable pageable) throws NotFoundException {
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

        if(!name.equals("") && category.equals("") && tradeMark.equals("")){
            products = productService.findAllByNameContaining(name, pageable);
        }else if(name.equals("") && !category.equals("") && tradeMark.equals("")){
            products = productService.findAllByCategory(category1, pageable);
        }else if(name.equals("") && category.equals("") && !tradeMark.equals("")){
            products = productService.findAllByTradeMark(tradeMark1, pageable);
        }else if(!name.equals("") && !category.equals("") && tradeMark.equals("")){
            products = productService.findAllByNameContainingAndCategory(name,category1, pageable);
        }else if(!name.equals("") && category.equals("") && !tradeMark.equals("")){
            products = productService.findAllByNameContainingAndTradeMark(name,tradeMark1, pageable);
        }else if(name.equals("") && !category.equals("") && !tradeMark.equals("")){
            products = productService.findAllByCategoryAndTradeMark(category1,tradeMark1, pageable);
        }else if(!name.equals("") && !category.equals("") && !tradeMark.equals("")){
            products = productService.findAllByNameContainingAndTradeMarkAndCategory(name,category1,tradeMark1, pageable);
        }else {
            products = productService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("product/list");
        modelAndView.addObject("products", products);
        modelAndView.addObject("cat_id", cat_id);
        modelAndView.addObject("trade_id", trade_id);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView getCreate() {
        ModelAndView modelAndView = new ModelAndView("product/create");
        modelAndView.addObject("product",new Products());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView postCreate(@ModelAttribute Products product) {
        MultipartFile file1 = product.getProductImage1();
        MultipartFile file2 = product.getProductImage2();
        MultipartFile file3 = product.getProductImage3();
        MultipartFile file4 = product.getProductImage4();
        String image1 = file1.getOriginalFilename();
        String image2 = file2.getOriginalFilename();
        String image3 = file3.getOriginalFilename();
        String image4 = file4.getOriginalFilename();
        String fileUpload = environment.getProperty("upload.path").toString();
        try {
            FileCopyUtils.copy(file1.getBytes(), new File(fileUpload + image1));
            FileCopyUtils.copy(file2.getBytes(), new File(fileUpload + image2));
            FileCopyUtils.copy(file3.getBytes(), new File(fileUpload + image3));
            FileCopyUtils.copy(file4.getBytes(), new File(fileUpload + image4));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Products products = new Products();
        products.setName(product.getName());
        products.setPrice(product.getPrice());
        products.setQuantity(product.getQuantity());
        products.setImage1(image1);
        products.setImage2(image2);
        products.setImage3(image3);
        products.setImage4(image4);
        products.setDescription(product.getDescription());
        products.setCategory(product.getCategory());
        products.setTradeMark(product.getTradeMark());
        productService.save(products);
        ModelAndView modelAndView = new ModelAndView("product/create");
        modelAndView.addObject("product", new Products());
        modelAndView.addObject("message", "Thêm sản phẩm mới thành công !!!");
        return modelAndView;
    }

    @GetMapping("/update/{id}")
    public ModelAndView getUpdate(@PathVariable Long id) throws NotFoundException {
        Products product = productService.findById(id).get();
        ModelAndView modelAndView = new ModelAndView("product/update");
        modelAndView.addObject("product",product);
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView postUpdate(@ModelAttribute Products product) throws NotFoundException {
        Products products = productService.findById(product.getId()).get();
        products.setName(product.getName());
        products.setPrice(product.getPrice());
        products.setQuantity(product.getQuantity());
        products.setDescription(product.getDescription());
        products.setCategory(product.getCategory());
        products.setTradeMark(product.getTradeMark());
        MultipartFile file1 = product.getProductImage1();
        MultipartFile file2 = product.getProductImage2();
        MultipartFile file3 = product.getProductImage3();
        MultipartFile file4 = product.getProductImage4();
        String image1;
        String image2;
        String image3;
        String image4;
        if (file1.isEmpty()){
            image1 = products.getImage1();
        }else {
            image1 = file1.getOriginalFilename();
        }
        if (file2.isEmpty()){
            image2 = products.getImage2();
        }else {
            image2 = file2.getOriginalFilename();
        }
        if (file3.isEmpty()){
            image3 = products.getImage4();
        }else {
            image3 = file3.getOriginalFilename();
        }
        if (file4.isEmpty()){
            image4 = products.getImage4();
        }else {
            image4 = file4.getOriginalFilename();
        }
        String fileUpload = environment.getProperty("upload.path").toString();
        try {
            FileCopyUtils.copy(file1.getBytes(), new File(fileUpload + image1));
            FileCopyUtils.copy(file2.getBytes(), new File(fileUpload + image2));
            FileCopyUtils.copy(file3.getBytes(), new File(fileUpload + image3));
            FileCopyUtils.copy(file4.getBytes(), new File(fileUpload + image4));
        } catch (IOException e) {
            e.printStackTrace();
        }
        products.setImage1(image1);
        products.setImage2(image2);
        products.setImage3(image3);
        products.setImage4(image4);
        productService.save(products);
        ModelAndView modelAndView = new ModelAndView("product/update");
        modelAndView.addObject("product", product);
        modelAndView.addObject("message", "Cập nhật rượu thành công !!!");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id, Pageable pageable) throws NotFoundException{
        Products product = productService.findById(id).get();
        productService.delete(product.getId());
        Page<Products> products = productService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("product/list");
        modelAndView.addObject("products", products);
        return modelAndView;
    }


}
