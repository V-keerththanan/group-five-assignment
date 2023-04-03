package com.groupfive.assignment.controller;

import com.groupfive.assignment.model.Order;
import com.groupfive.assignment.model.OrderItem;
import com.groupfive.assignment.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteOrder(@RequestParam Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/get-all-by-user")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam Integer id) {
        List<Order> orders = orderService.getOrdersByUserId(id);
        return ResponseEntity.ok(orders);
    }



    @PutMapping("/update")
    public ResponseEntity<Order> updateOrder(@RequestParam Long id, @RequestBody Order order) {

        orderService.updateOrderType(id,order.getStatus());
        return ResponseEntity.ok(order);
    }
    @GetMapping("/t items")
    public ResponseEntity<List<OrderItem>> getAllItemsInOrder(@RequestParam Long orderId) {
        List<OrderItem> items = orderService.getOrderItems(orderId);
        return ResponseEntity.ok(items);
    }


}
