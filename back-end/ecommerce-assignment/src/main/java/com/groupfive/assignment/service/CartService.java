package com.groupfive.assignment.service;

import com.groupfive.assignment.model.Cart;
import com.groupfive.assignment.model.CartItem;
import com.groupfive.assignment.model.Product;
import com.groupfive.assignment.model.User;
import com.groupfive.assignment.repository.CartRepository;
import com.groupfive.assignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
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

    public void addCartItem(long cart_id, Integer user_id, Product product, int quantity){

        Optional<Cart> existingCart=cartRepository.findById(cart_id);
        Cart actualCart=null;
        if(!existingCart.isPresent()){
            actualCart=createCart(user_id);
        }
        actualCart=existingCart.get();
        List<CartItem> cartItems = actualCart.getItems();
        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                cartRepository.save(actualCart);
                return;
            }
        }
        CartItem newItem = new CartItem();
        newItem.setProduct(product);
        newItem.setQuantity(quantity);
        newItem.setCart(actualCart);
        cartItems.add(newItem);
        cartRepository.save(actualCart);
    }

    public void removeCartItem(Cart cart, CartItem cartItem) {
        List<CartItem> cartItems = cart.getItems();
        cartItems.remove(cartItem);
        cartRepository.save(cart);
    }

    public void clearCart(Cart cart) {
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    }