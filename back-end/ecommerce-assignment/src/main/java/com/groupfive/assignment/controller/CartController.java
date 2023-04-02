package com.groupfive.assignment.controller;

import com.groupfive.assignment.model.Cart;
import com.groupfive.assignment.model.CartItem;
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

    @GetMapping
    public ResponseEntity<?> getCartById(@RequestParam Long cartId) {
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

    @GetMapping ("/add-item")
    public ResponseEntity<String> addCartItem(@RequestParam Long cartId, @RequestParam Long productId, @RequestParam int quantity) {
        cartService.addCartItem(cartId, productId, quantity);
        return ResponseEntity.ok("Item added to cart");
    }

    @DeleteMapping("/items/delete")
    public ResponseEntity<String> removeCartItem(
            @RequestParam Long cartId,
            @RequestParam Long itemId) {

        cartService.removeCartItem(cartId, itemId);
        return ResponseEntity.ok("removed from Cart");
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(@RequestParam("cartId") Long cartId) {

        cartService.clearCart(cartId);
        return ResponseEntity.ok("cleared cart items");
    }

}