package com.meuecommerce.api.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.meuecommerce.api.domain.model.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class ProductRepository {

    @PersistenceContext
    private EntityManager manager;

    public List<Product> listAll() {
        return manager.createQuery("from Product", Product.class).getResultList();
    }

    public Product findById(Long id) {
        return manager.find(Product.class, id);
    }

    @Transactional
    public Product save(Product product) {
        return manager.merge(product);
    }

    @Transactional
    public void remove(Long id) {
        Product product = findById(id);
        if (product != null){
            manager.remove(product);
        }
    }
}
