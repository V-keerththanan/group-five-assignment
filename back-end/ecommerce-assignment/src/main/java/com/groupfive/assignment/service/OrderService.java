package com.groupfive.assignment.service;

import com.groupfive.assignment._enum.OrderStatus;
import com.groupfive.assignment.email.EmailConfirmation;
import com.groupfive.assignment.email.EmailVerification;
import com.groupfive.assignment.model.Order;
import com.groupfive.assignment.model.OrderItem;
import com.groupfive.assignment.repository.OrderItemRepository;
import com.groupfive.assignment.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    private EmailConfirmation emailConfirmation;

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

