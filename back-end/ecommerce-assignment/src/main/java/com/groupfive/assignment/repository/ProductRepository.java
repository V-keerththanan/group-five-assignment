package com.groupfive.assignment.repository;

import com.groupfive.assignment._enum.ProductCategory;
import com.groupfive.assignment.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String query);
    List<Product> findByCategory(ProductCategory category);
    @Query("SELECT p FROM Product p WHERE p.available = true ORDER BY p.id DESC")
    List<Product> findLatestProducts(Pageable pageable);
    
}
