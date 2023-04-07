package com.groupfive.assignment.email;

import com.groupfive.assignment.model.Order;
import com.groupfive.assignment.model.User;
import com.groupfive.assignment.repository.OrderRepository;
import com.groupfive.assignment.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class EmailVerification {


    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JavaMailSender javaMailSender;

    public String generateOtp() {
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000);
        return String.valueOf(otpValue);
    }

    public void sendOtpEmail(String email, String otp) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("OTP for Verification");
        msg.setText("Thank you for connecting with us!\nPlease enter this OTP to complete your registration or verification process,  " +
                "Your OTP is: " + otp+"\n\nBest regards,\n" +
                "Eplanet");
        javaMailSender.send(msg);
    }

    public boolean verifyOtp(String email, String otp) {
        Optional<User> saveduUser=userRepo.findByEmail(email);
        if (saveduUser == null) {
            return false;
        }

        if (saveduUser.get().getOtp().equals(otp)) {
            saveduUser.get().setStatus(true);
            saveduUser.get().setOtp(null);
            userRepo.save(saveduUser.get());

            return true;
        }

        return false;
    }



}
