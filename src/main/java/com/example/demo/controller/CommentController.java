package com.example.demo.controller;

import com.example.demo.exeption.NotFoundException;
import com.example.demo.model.AppUser;
import com.example.demo.model.Comment;
import com.example.demo.model.Products;
import com.example.demo.service.appUser.IAppUserService;
import com.example.demo.service.comment.ICommentService;
import com.example.demo.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CommentController {
    @Autowired
    IProductService productService;

    @Autowired
    ICommentService commentService;

    @Autowired
    IAppUserService appUserService;

    @ModelAttribute()
    public AppUser currentUser(){
        return appUserService.getCurrentUser();
    }

    @PostMapping("/comment/create")
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment) throws NotFoundException {
        String strCmt = comment.getComment();
        Products products  = productService.findById(comment.getProduct().getId()).get();
        Comment comment1 = new Comment();
        comment1.setComment(strCmt);
        comment1.setProduct(products);
        comment1.setUser(currentUser());
        commentService.save(comment1);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<Iterable<Comment>> loadList(@PathVariable Long id) throws NotFoundException {
        Products products = productService.findById(id).get();
        Iterable<Comment> comments = commentService.findAllByProduct(products);
        return new ResponseEntity<>(comments,HttpStatus.OK);
    }

    @GetMapping("/comment/sum/{id}")
    public ResponseEntity getSum(@PathVariable Long id)throws NotFoundException {
        Products products = productService.findById(id).get();
        Integer cmtSum = commentService.countAllByProduct(products);
        return new ResponseEntity(cmtSum,HttpStatus.OK);
    }
}
