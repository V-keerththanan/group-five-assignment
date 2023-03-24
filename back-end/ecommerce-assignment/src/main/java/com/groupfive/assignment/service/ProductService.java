package com.groupfive.assignment.service;

import com.groupfive.assignment.model.Product;
import com.groupfive.assignment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepo;

    public List<Product> getAllProducts() {
        return productRepo.findAll();

    }

    public Product getProductById(Long id) {
        return productRepo.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {
        return productRepo.save(product);
    }

    public boolean deleteProductById(Long id) {
        if (productRepo.existsById(id)) {
            productRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Product> searchProducts(String query) {
        return productRepo.findByNameContainingIgnoreCase(query);
    }

}
