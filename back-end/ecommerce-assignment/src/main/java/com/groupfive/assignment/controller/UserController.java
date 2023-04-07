package com.groupfive.assignment.controller;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupfive.assignment.email.EmailVerification;
import com.groupfive.assignment.model.User;
import com.groupfive.assignment.service.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;


   @GetMapping("/user/all")
   @PreAuthorize("hasRole('ADMIN')")
   public ResponseEntity<List<User>> getAllUser() {

           return ResponseEntity.ok(userService.getAllUser());

   }


    @GetMapping("/user/get-details-by-id")
    public  ResponseEntity<User> getUserDetailsById(@RequestParam Integer userId) {
       User u=userService.getUserDetailsById(userId);
        return ResponseEntity.ok(u);
    }

}
