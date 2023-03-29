package com.groupfive.assignment.service;

import com.groupfive.assignment._enum.OrderStatus;
import com.groupfive.assignment.email.EmailConfirmation;
import com.groupfive.assignment.email.EmailVerification;
import com.groupfive.assignment.error.CartNotFoundException;
import com.groupfive.assignment.model.Cart;
import com.groupfive.assignment.model.CartItem;
import com.groupfive.assignment.model.Order;
import com.groupfive.assignment.model.OrderItem;
import com.groupfive.assignment.repository.CartRepository;
import com.groupfive.assignment.repository.OrderItemRepository;
import com.groupfive.assignment.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    private EmailConfirmation emailConfirmation;

    private CartRepository cartRepository;

    public Order placeOrder(Order order) {

        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PROCESSING);
        Order savedOrder = orderRepository.save(order);

        for (OrderItem orderItem : order.getOrderItems()) {
            orderItem.setOrder(savedOrder);
            orderItemRepository.save(orderItem);
        }

        emailConfirmation.sendConfirmationEmail(order.getId(), order.getUser().getEmail());
        return savedOrder;
    }

    @Transactional
    public Order placeOrder(Long cartId, List<CartItem> cartItems) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));


        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PROCESSING);
        orderRepository.save(order);


        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order);
            orderItem.calculatepriceOfOrderItem(cartItem.getProduct());
            orderItemRepository.save(orderItem);
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.calculateTotalAmount();
        orderRepository.save(order);
        cart.getItems().removeAll(cartItems);
        cartRepository.save(cart);

        return order;
    }
    public List<Order> getOrders(){
        return orderRepository.findAll();
    }
    public List<Order> getOrdersByUserId(Integer Id){
        return orderRepository.findByUserId(Id);
    }
    public void deleteOrderById(Long id) {
        Optional<Order> findOrder=orderRepository.findById(id);

        if (!findOrder.isPresent()) {
            throw new EntityNotFoundException("Order not found with ID: " + id);
        }
        orderItemRepository.deleteAll(findOrder.get().getOrderItems());

        orderRepository.delete(findOrder.get());
    }

    public Order updateOrderType(Long id, OrderStatus newType){
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (!orderOptional.isPresent()) {
            throw new EntityNotFoundException("Order not found with ID: " + id);
        }
        Order order = orderOptional.get();
        if (order.getStatus() == newType) {
            return order;
        }
        order.setStatus(newType);
        return orderRepository.save(order);
    }

    }

