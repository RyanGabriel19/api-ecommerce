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

import com.meuecommerce.api.controller.dto.CategoryResponseDTO;
import com.meuecommerce.api.controller.dto.ProductRequestDTO;
import com.meuecommerce.api.controller.dto.ProductResponseDTO;
import com.meuecommerce.api.domain.model.Category;
import com.meuecommerce.api.domain.model.Product;
import com.meuecommerce.api.domain.service.CategoryService;
import com.meuecommerce.api.domain.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<ProductResponseDTO> list() {
        return productService.findAll()
        .stream()
        .map(product -> this.toResponseDTO(product))
        .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductResponseDTO findById(@PathVariable Long id) {
        Product product = productService.findByIdOrThrow(id);
        return toResponseDTO(product);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO add(@Valid @RequestBody ProductRequestDTO productRequest) {
        Product product = toEntity(productRequest);

        Product productSaved = productService.save(product);

        return toResponseDTO(productSaved);
    }

    @PutMapping("/{id}")
    public ProductResponseDTO update(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO productRequest) {

        Product productData = toEntity(productRequest);

        Product productUpdated = productService.update(id, productData);

        return toResponseDTO(productUpdated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        productService.remove(id);
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

    private Product toEntity(ProductRequestDTO productRequest) {
        Product product = new Product();
        
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());

        if (productRequest.getCategoryId() != null) {
            Category category = categoryService.findByIdOrThrow(productRequest.getCategoryId());
            
            product.setCategory(category);
        }

        return product;
    }
}
