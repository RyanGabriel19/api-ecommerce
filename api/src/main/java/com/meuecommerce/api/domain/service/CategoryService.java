package com.meuecommerce.api.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.meuecommerce.api.domain.model.Category;
import com.meuecommerce.api.domain.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findByIdOrThrow(Long id) {
        return categoryRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada. ID: " + id));
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(Long id, Category categoryData) {
        Category existingCategory = findByIdOrThrow(id);

        existingCategory.setName(categoryData.getName());

        return categoryRepository.save(existingCategory);
    }

    public void remove(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Categoria não encontrada. ID: " + id);
        }

        categoryRepository.deleteById(id);
    }
}
