package com.example.demo.service.category;

import com.example.demo.model.Category;
import com.example.demo.service.IService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryService extends IService<Category>  {
    Page<Category> findAll(Pageable pageable);
    Page<Category> findAllByFirstNameContaining(String firstname, Pageable pageable);
    List<Category> findNewCategory();
}
