package com.example.demo.controller;

import com.example.demo.exeption.NotFoundException;
import com.example.demo.model.Category;
import com.example.demo.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    private Environment environment;

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView showInputNotAcceptable() {
        return new ModelAndView("/error/index");
    }

    @GetMapping("")
    public ModelAndView getList(@RequestParam("name") Optional<String> name,@PageableDefault(size = 10) Pageable pageable) {
        Page<Category> categories;
        if(name.isPresent()){
            categories = categoryService.findAllByFirstNameContaining(name.get(), pageable);
        } else {
            categories = categoryService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("category/list");
        modelAndView.addObject("category", categories);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView getCreate() {
        ModelAndView modelAndView = new ModelAndView("category/create");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView postCreate(@ModelAttribute Category category){
        MultipartFile file = category.getImg();
        String image = file.getOriginalFilename();
        String fileUpload = environment.getProperty("upload.path").toString();
        try {
            FileCopyUtils.copy(file.getBytes(), new File(fileUpload + image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Category category1 = new Category();
        category1.setName(category.getName());
        category1.setImage(image);
        categoryService.save(category1);
        ModelAndView modelAndView = new ModelAndView("category/create");
        modelAndView.addObject("category", new Category());
        modelAndView.addObject("message", "Thêm loại rượu mơi thành công !!!");
        return modelAndView;
    }

    @GetMapping("/update/{id}")
    public ModelAndView getUpdate(@PathVariable Long id) throws NotFoundException {
        ModelAndView modelAndView = new ModelAndView("category/update");
        Category category = categoryService.findById(id).get();
        modelAndView.addObject("category",category);
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView postUpdate(@ModelAttribute Category category) throws NotFoundException {
        Category category1 = categoryService.findById(category.getId()).get();
        category1.setName(category.getName());
        MultipartFile file = category.getImg();
        String image;
        if (file.isEmpty()){
            image = category1.getImage();
        } else {
            image = file.getOriginalFilename();
        }
        category1.setImage(image);
        String fileUpload = environment.getProperty("upload.path").toString();
        try {
            FileCopyUtils.copy(file.getBytes(), new File(fileUpload + image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        categoryService.save(category1);
        ModelAndView modelAndView = new ModelAndView("category/update");
        modelAndView.addObject("category", category);
        modelAndView.addObject("message", "Cập nhật loại rượu thành công !!!");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id, Pageable pageable) throws NotFoundException{
        Category category = categoryService.findById(id).get();
        categoryService.delete(category.getId());
        Page<Category> categories = categoryService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("category/list");
        modelAndView.addObject("category", categories);
        return modelAndView;
    }

}
