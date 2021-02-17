package com.example.demo.service.comment;

import com.example.demo.exeption.NotFoundException;
import com.example.demo.model.Comment;
import com.example.demo.model.Products;
import com.example.demo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CommentService implements ICommentService{
    @Autowired
    CommentRepository commentRepository;
    @Override
    public Iterable<Comment> findAll() {
        return null;
    }

    @Override
    public Optional<Comment> findById(Long id) throws NotFoundException {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Iterable<Comment> findAllByProduct(Products products) {
        return commentRepository.findAllByProductOrderByIdDesc(products);
    }

    @Override
    public Integer countAllByProduct(Products products) {
        return commentRepository.countAllByProduct(products);
    }

}
