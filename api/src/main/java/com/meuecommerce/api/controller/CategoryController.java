package com.meuecommerce.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meuecommerce.api.controller.dto.CategoryResponseDTO;
import com.meuecommerce.api.domain.model.Category;
import com.meuecommerce.api.domain.repository.CategoryRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<CategoryResponseDTO> list() {
        List<Category> categories = categoryRepository.listAll();

        return categories.stream()
            .map(category -> new CategoryResponseDTO(
                category.getId(),
                category.getName()
            ))
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CategoryResponseDTO findById(@PathVariable Long id) {
        Category category = categoryRepository.findById(id);

        CategoryResponseDTO categoryDTO = new CategoryResponseDTO(
            category.getId(),
            category.getName()
        );

        return categoryDTO;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category add(@Valid @RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        categoryRepository.remove(id);
    }
}
