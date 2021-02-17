package com.example.demo.service.category;

import com.example.demo.exeption.NotFoundException;
import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) throws NotFoundException {
        Optional<Category> category = categoryRepository.findById(id);
        if (category == null)  throw new NotFoundException();
        return category;
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Page<Category> findAllByFirstNameContaining(String firstname, Pageable pageable) {
        return categoryRepository.findAllByNameContaining(firstname, pageable);
    }

    @Override
    public List<Category> findNewCategory(){
        return categoryRepository.findNewCategory();
    }
}
