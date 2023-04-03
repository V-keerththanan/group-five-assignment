package com.groupfive.assignment.controller;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupfive.assignment.email.EmailVerification;
import com.groupfive.assignment.model.User;
import com.groupfive.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;


   @GetMapping("user/all")
    public ResponseEntity<List<User> > getAllUser(){
       return  ResponseEntity.ok(userService.getAllUser());
   }



  @DeleteMapping("/user")
  public ResponseEntity<Void> deleteUser(@RequestParam Integer userId) {
      boolean deleted = userService.deleteUserById(userId);
      if (!deleted) {
          return ResponseEntity.notFound().build();
      }
      return ResponseEntity.noContent().build();
  }



}
