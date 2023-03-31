package com.groupfive.assignment.repository;

import com.groupfive.assignment._enum.ProductCategory;
import com.groupfive.assignment.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String query);
    List<Product> findByCategory(ProductCategory category);
    
}
