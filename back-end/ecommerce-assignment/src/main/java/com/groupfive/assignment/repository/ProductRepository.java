package com.groupfive.assignment.repository;

import com.groupfive.assignment._enum.ProductCategory;
import com.groupfive.assignment.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String query);
    List<Product> findByCategory(ProductCategory category);
    
}
