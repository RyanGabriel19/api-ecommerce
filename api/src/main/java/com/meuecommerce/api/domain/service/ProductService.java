package com.meuecommerce.api.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.meuecommerce.api.domain.model.Product;
import com.meuecommerce.api.domain.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findByIdOrThrow(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado. ID: " + id));
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product update(Long id, Product productData) {
        Product existingProduct = findByIdOrThrow(id);

        existingProduct.setName(productData.getName());
        existingProduct.setDescription(productData.getDescription());
        existingProduct.setPrice(productData.getPrice());
        existingProduct.setStockQuantity(productData.getStockQuantity());
        existingProduct.setCategory(productData.getCategory());

        return productRepository.save(existingProduct);
    }

    public void remove(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Produto não encontrado. ID: " + id);
        }

        productRepository.deleteById(id);
    }
}
