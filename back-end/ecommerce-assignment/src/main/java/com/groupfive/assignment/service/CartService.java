package com.groupfive.assignment.service;

import com.groupfive.assignment.model.Cart;
import com.groupfive.assignment.model.CartItem;
import com.groupfive.assignment.model.Product;
import com.groupfive.assignment.model.User;
import com.groupfive.assignment.repository.CartRepository;
import com.groupfive.assignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    public Cart createCart(Integer user_id) {
        Optional<User> existingUser=userRepository.findById(user_id);
        Cart cart = new Cart();
        cart.setUser(existingUser.get());
        return cartRepository.save(cart);
    }

    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId).orElse(null);
    }

    public List<CartItem> getCartItems(Cart cart) {
        return cart.getItems();
    }

    public String addCartItem(long cart_id, Product product, int quantity) {
        Optional<Cart> existingCart = cartRepository.findById(cart_id);
        if (existingCart.isPresent()) {
            Cart cart = existingCart.get();
            List<CartItem> cartItems = cart.getItems();
            for (CartItem item : cartItems) {
                if (item.getProduct().getId().equals(product.getId())) {
                    item.setQuantity(item.getQuantity() + quantity);
                    cartRepository.save(cart);
                    return "Item quantity updated";
                }
            }
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
            cartItems.add(newItem);
            cartRepository.save(cart);
            return "Item added to cart";
        } else {
            return "Invalid cart ID";
        }
    }


    public void removeCartItem(Cart cart, CartItem cartItem) {
        List<CartItem> cartItems = cart.getItems();
        cartItems.remove(cartItem);
        cartRepository.save(cart);
    }

    public void clearCart(Cart cart) {
        cart.setItems(new ArrayList<>());
        cartRepository.save(cart);
    }

    public Cart createOrGetCart(int userId) {
        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            if (cart.getItems().isEmpty()) {
                return cart;
            }
            return cart;
        } else {
            return createCart(userId);
        }
    }


}