package com.groupfive.assignment.service;

import com.groupfive.assignment._enum.OrderStatus;
import com.groupfive.assignment.email.EmailConfirmation;
import com.groupfive.assignment.email.EmailVerification;
import com.groupfive.assignment.email.OrderCancel;
import com.groupfive.assignment.error.CartNotFoundException;
import com.groupfive.assignment.error.CartOrUserNotFound;
import com.groupfive.assignment.error.OrderNotFoundException;
import com.groupfive.assignment.model.*;
import com.groupfive.assignment.repository.CartRepository;
import com.groupfive.assignment.repository.OrderItemRepository;
import com.groupfive.assignment.repository.OrderRepository;
import com.groupfive.assignment.repository.UserRepository;
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

    @Autowired
    private EmailConfirmation emailConfirmation;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderCancel orderCancel;
    @Transactional
    public Order placeOrder(Integer userId, Long cartId,String homeNo,String homeStreet,String homeCity,String homeDistrict,String homePhoneNo) {


        Optional<User> savedUser=userRepository.findById(userId);
        Optional<Cart> savedCart=cartRepository.findById(cartId);
        if(savedUser.isPresent() && savedCart.isPresent()){
            Cart cart=savedCart.get();
            List<CartItem> cartItems=cart.getItems();
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime futureDateTime = now.plusDays(7);
            Order order = new Order();
            order.setUser(cart.getUser());
            order.setOrderDate(now);
            order.setStatus(OrderStatus.PROCESSING);
            order.setHomeNo(homeNo);
            order.setHomeStreet(homeStreet);
            order.setHomeCity(homeCity);
            order.setHomeDistrict(homeDistrict);
            order.setDeliveryDate(futureDateTime);
            order.setHomePhoneNo(homePhoneNo);
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

            emailConfirmation.sendConfirmationEmail(order.getId(),savedUser.get().getEmail());
            return order;

        }else{
            throw new CartOrUserNotFound("user or cart not valid....");

        }


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

    public List<OrderItem> getOrderItems(Long orderId){
        Optional<Order> savedOrder=orderRepository.findById(orderId);
        if (savedOrder.isPresent()){
            return savedOrder.get().getOrderItems();
        }else{
            throw new OrderNotFoundException("Order is not found !");
        }


    }

    public double getTotalAmountByOrderId(Long orderId){
        Optional<Order> savedOrder=orderRepository.findById(orderId);
        if (savedOrder.isPresent()){
            return savedOrder.get().getAmount();
        }else{
            throw new OrderNotFoundException("Order is not found !");
        }

    }


        public void cancelOrder(Long orderId) {
            Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found"));

            if (order.getStatus() == OrderStatus.CANCELLED) {
                throw new RuntimeException("Order is already cancelled");
            }

            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);

            //orderCancel.sendCancelEmail(orderId);
        }



}

