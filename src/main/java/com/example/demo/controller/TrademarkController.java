package com.example.demo.controller;

import com.example.demo.exeption.NotFoundException;
import com.example.demo.model.Category;
import com.example.demo.model.TradeMark;
import com.example.demo.service.category.CategoryService;
import com.example.demo.service.trademark.TrademarkService;
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
@RequestMapping("/admin/trademark")
public class TrademarkController {

    @Autowired
    TrademarkService trademarkService;

    @Autowired
    private Environment environment;

    @ExceptionHandler(NotFoundException.class)
    public ModelAndView showInputNotAcceptable() {
        return new ModelAndView("/error/index");
    }

    @GetMapping("")
    public ModelAndView getList(@RequestParam("name") Optional<String> name, @PageableDefault(size = 10) Pageable pageable) {
        Page<TradeMark> tradeMarks;
        if(name.isPresent()){
            tradeMarks = trademarkService.findAllByFirstNameContaining(name.get(), pageable);
        } else {
            tradeMarks = trademarkService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("trademark/list");
        modelAndView.addObject("trademark", tradeMarks);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView getCreate() {
        ModelAndView modelAndView = new ModelAndView("trademark/create");
        modelAndView.addObject("trademark", new TradeMark());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView postCreate(@ModelAttribute TradeMark tradeMark){
        MultipartFile file = tradeMark.getImg();
        String image = file.getOriginalFilename();
        String fileUpload = environment.getProperty("upload.path").toString();
        try {
            FileCopyUtils.copy(file.getBytes(), new File(fileUpload + image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        TradeMark tradeMark1 = new TradeMark();
        tradeMark1.setName(tradeMark.getName());
        tradeMark1.setImage(image);
        trademarkService.save(tradeMark1);
        ModelAndView modelAndView = new ModelAndView("trademark/create");
        modelAndView.addObject("trademark", new TradeMark());
        modelAndView.addObject("message", "Thêm thương hiệu mơi thành công !!!");
        return modelAndView;
    }

    @GetMapping("/update/{id}")
    public ModelAndView getUpdate(@PathVariable Long id) throws NotFoundException {
        ModelAndView modelAndView = new ModelAndView("trademark/update");
        TradeMark tradeMark = trademarkService.findById(id).get();

        modelAndView.addObject("trademark",tradeMark);
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView postUpdate(@ModelAttribute TradeMark tradeMark) throws NotFoundException {
        TradeMark tradeMark1 = trademarkService.findById(tradeMark.getId()).get();
        tradeMark.setName(tradeMark.getName());
        MultipartFile file = tradeMark.getImg();
        String image;
        if (file.isEmpty()){
            image = tradeMark1.getImage();
        } else {
            image = file.getOriginalFilename();
        }
        tradeMark1.setImage(image);
        String fileUpload = environment.getProperty("upload.path").toString();
        try {
            FileCopyUtils.copy(file.getBytes(), new File(fileUpload + image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        trademarkService.save(tradeMark1);
        ModelAndView modelAndView = new ModelAndView("trademark/update");
        modelAndView.addObject("trademark", tradeMark);
        modelAndView.addObject("message", "Cập nhật thương hiệu thành công !!!");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id, Pageable pageable) throws NotFoundException{
        TradeMark tradeMark = trademarkService.findById(id).get();
        trademarkService.delete(tradeMark.getId());
        Page<TradeMark> tradeMarks = trademarkService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/trademark/list");
        modelAndView.addObject("trademark", tradeMarks);
        return modelAndView;
    }
}
