package com.groupfive.assignment.controller;

import com.groupfive.assignment.dto.Request.CheckoutRequest;
import com.groupfive.assignment.model.Order;
import com.groupfive.assignment.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckoutController {
    @Autowired
    private CheckoutService checkoutService;
    @PostMapping("/checkout")
    public ResponseEntity<String> processCheckout(@RequestBody CheckoutRequest checkoutRequest) {


        checkoutService.processCheckout(checkoutRequest.getUserId(),
                checkoutRequest.getCartId(), checkoutRequest.getHomeNo(),
                checkoutRequest.getHomeStreet(),checkoutRequest.getHomeCity(),
                checkoutRequest.getHomeDistrict(),checkoutRequest.getHomePhoneNo());

        return ResponseEntity.ok("Successfully proceed");
    }
}
