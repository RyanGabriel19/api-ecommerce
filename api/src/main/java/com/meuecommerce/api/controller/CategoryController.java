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

import com.meuecommerce.api.controller.dto.CategoryRequestDTO;
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
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
            .map(category -> toResponseDTO(category))
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CategoryResponseDTO findById(@PathVariable Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Não encontrado"));

        return toResponseDTO(category);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDTO add(@Valid @RequestBody CategoryRequestDTO categoryRequest) {
        Category category = toEntity(categoryRequest);

        Category savedCategory = categoryRepository.save(category);
        
        return toResponseDTO(savedCategory);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Categoria não encontrada para remoção. ID: " + id);
        }

        categoryRepository.deleteById(id);
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
