package com.groupfive.assignment.service;

import com.groupfive.assignment._enum.OrderStatus;
import com.groupfive.assignment.model.Order;
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
    private OrderRepository orderRepo;



    public Order saveOrder(Order order) {
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PROCESSING);
        order.setPrice(order.getProduct());
        Order savedOrder = orderRepo.save(order);
        return savedOrder;
    }

    public List<Order> getAllOrder(){
        return orderRepo.findAll();
    }

    public List<Order> getAllOrderByUserId(Integer id){
        return orderRepo.findByUserId(id);
    }


    public void deleteOrderById(Long id){
        orderRepo.deleteById(id);
    }

    public Order updateOrderType(Long orderId, OrderStatus newType) {
        Optional<Order> orderOptional = orderRepo.findById(orderId);
        if (!orderOptional.isPresent()) {
            throw new EntityNotFoundException("Order not found with ID: " + orderId);
        }
        Order order = orderOptional.get();
        if (order.getStatus() == newType) {
            return order;
        }
        order.setStatus(newType);
        return orderRepo.save(order);
    }



}
