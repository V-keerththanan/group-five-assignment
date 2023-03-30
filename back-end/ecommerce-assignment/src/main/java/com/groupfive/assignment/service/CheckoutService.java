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
    public void processCheckout(User user, Cart cart, String homeNo,String homeStreet,String homeCity,String homeDistrict) {
            Order order=orderService.placeOrder(user,cart,homeNo,homeStreet,homeCity,homeDistrict);

            emailConfirmation.sendConfirmationEmail(order.getId(),user.getEmail());
    }
}
