package com.meuecommerce.api.controller.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductRequestDTO {

    @NotBlank(message = "O nome do produto é obrigatório")
    private String name;

    @NotBlank(message = "A descrição do produto é obrigatória")
    private String description;

    @NotNull(message = "O preço do produto é obrigatório")
    @Positive(message = "O preço deve ser maior do que zero")
    private BigDecimal price;

    @Positive(message = "O estoque deve ser maior ou igual a zero")
    @NotNull(message = "O estoque é obrigatório")
    private Integer stockQuantity;

    @NotNull(message = "O ID da categoria é obrigatório")
    private Long categoryId;

    public String getName() { return name; }
    public void setName(String name) {this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) {this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) {this.price = price;}

    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) {this.stockQuantity = stockQuantity; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
}
