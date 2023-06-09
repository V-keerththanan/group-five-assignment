package com.groupfive.assignment.service;

import com.groupfive.assignment.error.CartOrCartItemNotFoundException;
import com.groupfive.assignment.error.CartOrProductNotFoundException;
import com.groupfive.assignment.model.Cart;
import com.groupfive.assignment.model.CartItem;
import com.groupfive.assignment.model.Product;
import com.groupfive.assignment.model.User;
import com.groupfive.assignment.repository.CartItemRepository;
import com.groupfive.assignment.repository.CartRepository;
import com.groupfive.assignment.repository.ProductRepository;
import com.groupfive.assignment.repository.UserRepository;
import jakarta.persistence.EntityManager;
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
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

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

    public String addCartItem(long cart_id, Long product_id, int quantity) {
        Optional<Cart> existingCart = cartRepository.findById(cart_id);
        Optional<Product> existingProduct=productRepository.findById(product_id);
        if (existingCart.isPresent() && existingProduct.isPresent()) {
            Cart cart = existingCart.get();
            Product product=existingProduct.get();
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
            throw new CartOrProductNotFoundException("cart or product not found...");
        }
    }


    public void removeCartItem(Long cartId, Long cartItemId) {
        Optional<Cart> savedCart=cartRepository.findById(cartId);
        Optional<CartItem> savedCartItem=cartItemRepository.findById(cartItemId);
        if(savedCart.isPresent() && savedCartItem.isPresent()){
            List<CartItem> cartItems = savedCart.get().getItems();
            cartItems.remove(savedCartItem.get());
            cartRepository.save(savedCart.get());

        }else{
            throw new CartOrCartItemNotFoundException("given cart or cart item is not present ");
        }
    }

    public void clearCart(Long cartId) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            List<CartItem> cartItems = cart.getItems();
            cartItems.clear();
            entityManager.detach(cart);
            cartRepository.save(cart);

        }
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