package com.meuecommerce.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meuecommerce.api.controller.dto.CategoryRequestDTO;
import com.meuecommerce.api.controller.dto.CategoryResponseDTO;
import com.meuecommerce.api.domain.model.Category;
import com.meuecommerce.api.domain.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryResponseDTO> list() {
        List<Category> categories = categoryService.findAll();

        return categories.stream()
            .map(category -> toResponseDTO(category))
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CategoryResponseDTO findById(@PathVariable Long id) {

        Category category = categoryService.findByIdOrThrow(id);

        return toResponseDTO(category);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDTO add(@Valid @RequestBody CategoryRequestDTO categoryRequest) {
        Category category = toEntity(categoryRequest);

        Category savedCategory = categoryService.save(category);
        
        return toResponseDTO(savedCategory);
    }

    @PutMapping("/{id}")
    public CategoryResponseDTO update(@PathVariable Long id, @Valid @RequestBody CategoryRequestDTO categoryRequest) {
        Category existingCategory = categoryService.findByIdOrThrow(id);

         existingCategory.setName(categoryRequest.getName());

         Category updatedCategory = categoryService.save(existingCategory);

         return toResponseDTO(updatedCategory);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        categoryService.remove(id);
    }

    private CategoryResponseDTO toResponseDTO(Category category) {
        return new CategoryResponseDTO(
            category.getId(),
            category.getName()
        );
    }

    private Category toEntity(CategoryRequestDTO categoryRequest) {
        Category category = new Category();

        category.setName(categoryRequest.getName());
        
        return category;
    }
}
