package com.example.demo.repository;

import com.example.demo.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category,Long>, CrudRepository<Category, Long> {
    Page<Category> findAllByNameContaining(String firstname, Pageable pageable);
    @Query(value = "select  * from category order by id desc limit 3", nativeQuery = true)
    List<Category> findNewCategory();

}
