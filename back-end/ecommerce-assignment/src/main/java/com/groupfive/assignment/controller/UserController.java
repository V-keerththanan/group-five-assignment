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
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailVerification mailService;

   @GetMapping("/all")
    public ResponseEntity<List<User> > getAllUser(){
       return  ResponseEntity.ok(userService.getAllUser());
   }


    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        if (mailService.verifyOtp(email, otp)) {
            return ResponseEntity.ok("OTP verified successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
        }
    }
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
      boolean deleted = userService.deleteUserById(id);
      if (!deleted) {
          return ResponseEntity.notFound().build();
      }
      return ResponseEntity.noContent().build();
  }



}
