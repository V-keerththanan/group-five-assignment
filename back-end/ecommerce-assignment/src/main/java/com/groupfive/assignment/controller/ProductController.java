package com.groupfive.assignment.controller;

import com.groupfive.assignment._enum.ProductCategory;
import com.groupfive.assignment.model.Product;
import com.groupfive.assignment.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product/get-all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/get")
    public ResponseEntity<Product> getProductById(@RequestParam Long productId) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping("/admin/product/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }


    @DeleteMapping("/admin/product/delete")
    public ResponseEntity<Void> deleteProduct(@RequestParam Long productId) {
        boolean deleted = productService.deleteProductById(productId);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search-name")
    public ResponseEntity<List<Product>> searchProductName(@RequestParam String name) {
        List<Product> productList = productService.searchProductsByName(name);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/search-category")
    public ResponseEntity<List<Product>> searchProductCategory(@RequestParam ProductCategory category) {
        List<Product> productList = productService.searchProductsByCategory(category);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PutMapping("/admin/product/availability")
    public ResponseEntity<String> updateProductAvailability(@RequestParam Long productId, @RequestParam Boolean available) {
         productService.updateProduct(productId,available);

        return ResponseEntity.ok("Product availability updated successfully.");
    }

    @GetMapping("/getNewArrival")
    public ResponseEntity<List<Product>> getLatestProduct() {
        List<Product> productList = productService.getLatestProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }
}
