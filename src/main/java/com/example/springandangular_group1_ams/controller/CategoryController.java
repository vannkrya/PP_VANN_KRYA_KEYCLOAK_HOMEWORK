package com.example.springandangular_group1_ams.controller;

import com.example.springandangular_group1_ams.model.dto.CategoryDto;
import com.example.springandangular_group1_ams.model.request.CategoryRequest;
import com.example.springandangular_group1_ams.model.respone.ApiResponse;
import com.example.springandangular_group1_ams.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;


    @GetMapping("/")
    private ResponseEntity<?> getAllCategories(
            @RequestParam (defaultValue = "1") Integer page,
            @RequestParam (defaultValue = "5") Integer size
    ){
        var categories = categoryService.getAllCategories((page-1), size);
        ApiResponse<List<CategoryDto>> response = ApiResponse.<List<CategoryDto>>builder()
                .message("successfully fetched categories")
                .status("200")
                .payload(categories.getContent())
                .page(page)
                .size(size)
                .totalElements((int) categories.getTotalElements())
                .totalPages(categories.getTotalPages())
                .build();
        return ResponseEntity.ok(response);
    }
    @PostMapping("/")
    public ResponseEntity<?> addCategory(@Valid  @RequestBody CategoryRequest categoryRequest){
        var category = categoryService.addCategory(categoryRequest);
        ApiResponse<CategoryDto> response =ApiResponse.<CategoryDto>builder()
                .message("successfully save category")
                .status("200")
                .payload(category)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        try {
            CategoryDto category = categoryService.getCategoryById(id);
            ApiResponse<CategoryDto> response = ApiResponse.<CategoryDto>builder()
                    .message("success to fetch category by id:" + id)
                    .status("200")
                    .payload(category)
                    .build();
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            ApiResponse<String> response = ApiResponse.<String>builder()
                    .message("Category not found with id :" + id)
                    .status("206")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id , @RequestBody CategoryRequest categoryRequest){
        try {
            var category = categoryService.updateCategory(id,categoryRequest);
            ApiResponse<CategoryDto> response = ApiResponse.<CategoryDto>builder()
                    .message("success to fetch category by id:" + id)
                    .status("200")
                    .payload(category)
                    .build();
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            ApiResponse<String> response = ApiResponse.<String>builder()
                    .message("can't update this category id::" + id)
                    .status("500")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id){
        try {
            categoryService.deleteCategory(id);
            ApiResponse<CategoryDto> response = ApiResponse.<CategoryDto>builder()
                    .message("success to delete category by id:" + id)
                    .status("200")
                    .build();
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            ApiResponse<String> response = ApiResponse.<String>builder()
                    .message("can't delete this category id::" + id)
                    .status("500")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
    @GetMapping("/name")
    public ResponseEntity<?> getCategoriesByName(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam String name
    ) {
        Page<CategoryDto> categoryPage = categoryService.getCategoriesByName((page-1), size, name);

        List<CategoryDto> categories  = categoryPage.getContent();
        if (categories.isEmpty()) {
            ApiResponse<String> noCategoriesResponse = ApiResponse.<String>builder()
                    .message("No categories")
                    .status("500")
                    .build();
            return ResponseEntity.ok(noCategoriesResponse);
        } else {
            ApiResponse<?> categoryResponse = ApiResponse.builder()
                    .message("Successfully fetched categories")
                    .status("200")
                    .payload(categories)
                    .page(page)
                    .size(size)
                    .totalElements((int) categoryPage.getTotalElements())
                    .totalPages(categoryPage.getTotalPages())
                    .build();

            return ResponseEntity.ok(categoryResponse);
        }
    }
}


