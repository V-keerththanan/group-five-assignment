package com.groupfive.assignment.controller;


import com.groupfive.assignment.dto.Request.AuthenticationRequest;
import com.groupfive.assignment.dto.Request.RegisterRequest;
import com.groupfive.assignment.dto.Response.AuthenticationResponse;
import com.groupfive.assignment.model.User;
import com.groupfive.assignment.service.AuthenticationService;
import com.groupfive.assignment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

@Autowired
  private final AuthenticationService service;


  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.register(request));
  }


  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }


}
