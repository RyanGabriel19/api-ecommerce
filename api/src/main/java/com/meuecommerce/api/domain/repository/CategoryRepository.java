package com.meuecommerce.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meuecommerce.api.domain.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
