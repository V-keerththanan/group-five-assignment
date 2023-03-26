package com.groupfive.assignment.service;

import com.groupfive.assignment._enum.OrderStatus;
import com.groupfive.assignment.model.Order;
import com.groupfive.assignment.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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



}
