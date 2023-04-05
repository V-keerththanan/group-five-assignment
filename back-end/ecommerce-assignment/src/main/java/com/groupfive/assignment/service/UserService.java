package com.groupfive.assignment.service;


import com.groupfive.assignment.email.EmailVerification;
import com.groupfive.assignment.model.User;
import com.groupfive.assignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;


    BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public List<User> getAllUser(){
        return userRepo.findAll();
    }
    public User addUser(User user) {

        String encryptedPassword=passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return userRepo.save(user);
    }

    public User getUserDetailsById(Integer user_id){

        Optional<User> existUser=userRepo.findById(user_id);
        return existUser.get();

    }



    public boolean deleteUserById(Integer id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
            return true;
        }
        return false;
    }
}

