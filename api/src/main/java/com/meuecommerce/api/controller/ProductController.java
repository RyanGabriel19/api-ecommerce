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
        List<Product> products = productRepository.listAll();

        return products.stream()
            .map(product -> new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                new CategoryResponseDTO(
                    product.getCategory().getId(),
                    product.getCategory().getName()
                )
            ))
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductResponseDTO findById(@PathVariable Long id) {
        Product product = productRepository.findById(id);

        ProductResponseDTO productDTO = new ProductResponseDTO(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getStockQuantity(),
            new CategoryResponseDTO(
                product.getCategory().getId(),
                product.getCategory().getName()
            )
        );

        return productDTO;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product add(@Valid @RequestBody Product product) {
        return productRepository.save(product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        productRepository.remove(id);
    }

}
