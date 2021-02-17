package com.example.demo.repository;

import com.example.demo.model.Comment;
import com.example.demo.model.Products;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment,Long> {
    Iterable<Comment> findAllByProductOrderByIdDesc(Products products);
    Integer countAllByProduct(Products products);
}
