package com.example.springandangular_group1_ams.service.serviceImpl;

import com.example.springandangular_group1_ams.exception.NotFoundExceptionClass;
import com.example.springandangular_group1_ams.model.dto.CategoryDto;
import com.example.springandangular_group1_ams.model.entity.Category;
import com.example.springandangular_group1_ams.model.request.CategoryRequest;
import com.example.springandangular_group1_ams.repository.CategoryRepository;
import com.example.springandangular_group1_ams.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
        private final CategoryRepository categoryRepository;

    @Override
    public Page<CategoryDto> getAllCategories(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<CategoryDto> pageResult = categoryRepository.findAll(pageable).map(Category::toDto);
        return pageResult;
    }

    @Override
    public CategoryDto addCategory(CategoryRequest categoryRequest) {
        var categoryEntity = categoryRequest.toEntity();
        if (categoryEntity.getName().isEmpty()){
            throw new NotFoundExceptionClass("field name cannot be empty");
        }
        return categoryRepository.save(categoryEntity).toDto();
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        return category.toDto();
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        category.setName(categoryRequest.getName());
        if (category.getName().isEmpty()){
            throw new NotFoundExceptionClass("field name cannot be empty");
        }
        Category updatedCategory = categoryRepository.save(category);
        return updatedCategory.toDto();
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        categoryRepository.delete(category);
    }

    @Override
    public Page<CategoryDto> getCategoriesByName(Integer page, Integer size, String name) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryRepository.findByNameContainingIgnoreCase(pageable, name);

        List<CategoryDto> categoryDtoList = categoryPage.getContent()
                .stream()
                .map(category -> new CategoryDto(category.getId(), category.getName()))
                .collect(Collectors.toList());

        return new PageImpl<>(categoryDtoList, pageable, categoryPage.getTotalElements());

    }

}
