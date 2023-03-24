package com.groupfive.assignment.controller;



import com.groupfive.assignment.email.EmailService;
import com.groupfive.assignment.model.User;
import com.groupfive.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService mailService;

   @GetMapping("/user")
    public List<User> getAllUser(){
       return  userService.getAllUser();
   }


    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        if (mailService.verifyOtp(email, otp)) {
            return ResponseEntity.ok("OTP verified successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
        }
    }
  @DeleteMapping("/user/{id}")
  public ResponseEntity<Void> deleteCustomerById(@PathVariable Integer id) {
      userService.deleteUserById(id);
      return ResponseEntity.noContent().build();
  }


}