package com.meuecommerce.api.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.meuecommerce.api.domain.model.Category;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class CategoryRepository {

    @PersistenceContext
    private EntityManager manager;

    public List<Category> listAll() {
        return manager.createQuery("from Category", Category.class).getResultList();
    }

    public Category findById(Long id) {
        return manager.find(Category.class, id);
    }

    @Transactional
    public Category save(Category category) {
        return manager.merge(category);
    }

    @Transactional
    public void remove(Long id) {
        Category category = findById(id);
        if (category != null) {
            manager.remove(category);
        }
    }
}
