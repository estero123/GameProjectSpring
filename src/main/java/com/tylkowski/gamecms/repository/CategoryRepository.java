package com.tylkowski.gamecms.repository;

import com.tylkowski.gamecms.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
    Category findByCategorySlogan(String categorySlogan);
}
