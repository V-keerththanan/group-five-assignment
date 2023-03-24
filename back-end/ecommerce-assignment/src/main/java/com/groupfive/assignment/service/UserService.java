package com.groupfive.assignment.service;


import com.groupfive.assignment.dto.Request.*;
import com.groupfive.assignment.email.EmailService;
import com.groupfive.assignment.model.User;
import com.groupfive.assignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EmailService mailService;
//    LoginRequest loginRequest;
    BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public List<User> getAllUser(){
        return userRepo.findAll();
    }
    public User addUser(User user) {

        String encryptedPassword=passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return userRepo.save(user);
    }
    public User register(User user) {
        String encryptedPassword=passwordEncoder.encode(user.getPassword());
        // Generate OTP
        String otp = mailService.generateOtp();
        user.setOtp(otp);
        user.setStatus(false);
        user.setPassword(encryptedPassword);

        // Save user
        User savedUser = userRepo.save(user);

        // Send OTP email
        mailService.sendOtpEmail(user.getEmail(), otp);

        return savedUser;
    }



//    public boolean authenticate(@RequestBody com.group02.mobileshopsystem.api.Payload.Request.AuthenticationRequest loginRequest) {
//        User user = userRepo.findByEmail(loginRequest.getEmail());
//        if (user != null && passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())) {
//            return true;
//        }
//        return false;
//    }
    public void deleteUserById(Integer id) {
        userRepo.deleteById(id);
    }
}

