package com.tylkowski.gamecms.service;

import com.tylkowski.gamecms.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<Category> findAll(Pageable pageable);
    Category findByCategorySlogan(String categorySlogan);
    Iterable<Category> findAll();
    Category findOne(Long categoryId);
    void save(Category category);
    void delete(Category category);
}
