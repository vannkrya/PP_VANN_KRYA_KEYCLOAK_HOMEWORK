package com.example.springandangular_group1_ams.service;

import com.example.springandangular_group1_ams.model.dto.CategoryDto;
import com.example.springandangular_group1_ams.model.request.CategoryRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    Page<CategoryDto> getAllCategories(Integer page, Integer size);

    CategoryDto addCategory(CategoryRequest categoryRequest);

    CategoryDto getCategoryById(Long id);

    CategoryDto updateCategory(Long id, CategoryRequest categoryRequest);

    void deleteCategory(Long id);

    Page<CategoryDto> getCategoriesByName(Integer page, Integer size, String name);

}
