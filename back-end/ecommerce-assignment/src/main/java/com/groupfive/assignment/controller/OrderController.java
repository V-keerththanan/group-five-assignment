package com.groupfive.assignment.controller;

import com.groupfive.assignment.model.Order;
import com.groupfive.assignment.model.OrderItem;
import com.groupfive.assignment.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteOrder(@RequestParam Long orderId) {
        orderService.deleteOrderById(orderId);
        return ResponseEntity.ok("deleted..");
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/get-all-by-user")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam Integer userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }



    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> updateOrder(@RequestParam Long id, @RequestBody Order order) {

        orderService.updateOrderType(id,order.getStatus());
        return ResponseEntity.ok(order);
    }
    @GetMapping("/items")
    public ResponseEntity<List<OrderItem>> getAllItemsInOrder(@RequestParam Long orderId) {
        List<OrderItem> items = orderService.getOrderItems(orderId);
        return ResponseEntity.ok(items);
    }

    @PostMapping ("/cancel")
    public ResponseEntity<String> cancelOrder(@RequestParam Long orderId) {

        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("Order has been cancelled successfully");
    }


}
