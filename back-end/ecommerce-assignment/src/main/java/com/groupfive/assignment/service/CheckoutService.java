package com.groupfive.assignment.service;

import com.groupfive.assignment.email.EmailConfirmation;
import com.groupfive.assignment.model.Cart;
import com.groupfive.assignment.model.Order;
import com.groupfive.assignment.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckoutService {
    @Autowired
    private OrderService orderService;
    private EmailConfirmation emailConfirmation;
    public void processCheckout(Integer userId, Long cartId, String homeNo,String homeStreet,String homeCity,String homeDistrict,String homePhoneNo) {
            Order order=orderService.placeOrder(userId,cartId,homeNo,homeStreet,homeCity,homeDistrict,homePhoneNo);

    }
}
