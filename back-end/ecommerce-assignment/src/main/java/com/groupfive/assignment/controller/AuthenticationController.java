package com.groupfive.assignment.controller;


import com.groupfive.assignment.dto.Request.AuthenticationRequest;
import com.groupfive.assignment.dto.Request.RegisterRequest;
import com.groupfive.assignment.dto.Response.AuthenticationResponse;
import com.groupfive.assignment.email.EmailVerification;
import com.groupfive.assignment.model.User;
import com.groupfive.assignment.service.AuthenticationService;
import com.groupfive.assignment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  @Autowired
  private final AuthenticationService service;
  @Autowired
  private EmailVerification mailService;

  @PostMapping("/register")
  public ResponseEntity<String> registerUser(
      @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.registerUser(request));
  }



  @PostMapping("/log-in")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/verify")
  public ResponseEntity<String> verifyOtp(@RequestParam(name = "email") String email, @RequestParam String otp) {
    if (mailService.verifyOtp(email, otp)) {
      return ResponseEntity.ok("OTP verified successfully");
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
    }
  }



}
