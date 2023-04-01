package com.groupfive.assignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.groupfive.assignment._enum.OrderStatus;
import com.groupfive.assignment._enum.PaymentMethod;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Column(nullable = true)
    private double amount;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    private String homeNo;
    private String homeStreet;
    private String homeCity;
    private String homeDistrict;
    private String homePhoneNo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymethod=PaymentMethod.CASH_ON_DELIVERY;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<OrderItem> orderItems = new ArrayList<>();



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getAmount(){
        return this.amount;
    }

    public void calculateTotalAmount() {
        double totalAmount = 0.0;
        for (OrderItem item : this.orderItems) {
            totalAmount+=  item.getPrice();
        }
        this.amount =totalAmount ;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getHomeNo() {
        return homeNo;
    }

    public void setHomeNo(String homeNo) {
        this.homeNo = homeNo;
    }

    public String getHomeStreet() {
        return homeStreet;
    }

    public void setHomeStreet(String homeStreet) {
        this.homeStreet = homeStreet;
    }

    public String getHomeCity() {
        return homeCity;
    }

    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    public String getHomeDistrict() {
        return homeDistrict;
    }

    public void setHomeDistrict(String homeDistrict) {
        this.homeDistrict = homeDistrict;
    }

    public String getHomePhoneNo() {
        return homePhoneNo;
    }

    public void setHomePhoneNo(String homePhoneNo) {
        this.homePhoneNo = homePhoneNo;
    }

    public PaymentMethod getPaymethod() {
        return paymethod;
    }
}
