package com.groupfive.assignment.controller;

import com.groupfive.assignment.model.Cart;
import com.groupfive.assignment.model.CartItem;
import com.groupfive.assignment.model.Product;
import com.groupfive.assignment.repository.CartItemRepository;
import com.groupfive.assignment.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemRepository cartItemRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createCart(@PathVariable Integer user_id) {
        Cart cart = cartService.createCart(user_id);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<?> getCartById(@PathVariable Long cartId) {
        Cart cart = cartService.getCartById(cartId);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/items")
    public ResponseEntity<?> getCartItems(@RequestParam  Long cartId) {
        Cart cart = cartService.getCartById(cartId);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        List<CartItem> cartItems = cartService.getCartItems(cart);
        return ResponseEntity.ok(cartItems);
    }

    @PostMapping("/add-item")
    public ResponseEntity<String> addCartItem(@RequestParam Long cartId, @RequestBody Product product, @RequestParam int quantity) {
        cartService.addCartItem(cartId, product, quantity);
        return ResponseEntity.ok("Item added to cart");
    }

    @DeleteMapping("/items/delete")
    public ResponseEntity<?> removeCartItem(
            @RequestParam Long cartId,
            @RequestParam Long itemId) {
        Cart cart = cartService.getCartById(cartId);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        Optional<CartItem> cartItem = cartItemRepository.findById(itemId);
        if (cartItem == null) {
            return ResponseEntity.notFound().build();
        }
        cartService.removeCartItem(cart, cartItem.get());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(@RequestParam("cartId") Long cartId) {
        Cart cart = cartService.getCartById(cartId);
        cartService.clearCart(cart);
        return ResponseEntity.ok().build();
    }

}