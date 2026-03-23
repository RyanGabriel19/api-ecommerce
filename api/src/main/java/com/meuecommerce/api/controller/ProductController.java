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
import com.meuecommerce.api.controller.dto.ProductResponseDTO;
import com.meuecommerce.api.domain.model.Product;
import com.meuecommerce.api.domain.repository.ProductRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<ProductResponseDTO> list() {
        return productRepository.findAll()
        .stream()
        .map(product -> this.toResponseDTO(product))
        .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductResponseDTO findById(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Não encontrado"));

        return toResponseDTO(product);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO add(@Valid @RequestBody Product product) {
        Product productSaved = productRepository.save(product);
        return toResponseDTO(productSaved);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Produto não encontrado para remoção. ID: " + id);
        }

        productRepository.deleteById(id);
    }

    private ProductResponseDTO toResponseDTO (Product product) {
        CategoryResponseDTO categoryDTO = null;

        if (product.getCategory() != null) {
            categoryDTO = new CategoryResponseDTO(
                product.getCategory().getId(),
                product.getCategory().getName()
            );
        }

        return new ProductResponseDTO(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getStockQuantity(),
            categoryDTO
        );
    }
}
