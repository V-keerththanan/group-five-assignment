package com.groupfive.assignment.service;

import com.groupfive.assignment._enum.ProductCategory;
import com.groupfive.assignment.model.Product;
import com.groupfive.assignment.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Product> searchProductsByName(String query) {
        return productRepo.findByNameContainingIgnoreCase(query);
    }

    public List<Product> searchProductsByCategory(ProductCategory category) {
        return productRepo.findByCategory(category);
    }

   public void updateProduct(Long id,Boolean available){
       Optional<Product> product = productRepo.findById(id);

       if (!product.isPresent()) {
           throw new RuntimeException("Product not found with this id");
       }
       Product savedProduct=product.get();
       savedProduct.setAvailable(available);
       productRepo.save(savedProduct);

   }



}
