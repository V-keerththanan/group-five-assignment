package com.groupfive.assignment.email;

import com.groupfive.assignment.error.OrderNotFoundException;
import com.groupfive.assignment.model.Order;
import com.groupfive.assignment.model.OrderItem;
import com.groupfive.assignment.repository.OrderRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class OrderCancel {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private OrderRepository orderRepository;

    private void sendCancelEmail(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found"));
        String subject = "Order Cancellation Confirmation";
        String body = "Dear " + order.getUser().getFirstname() + ",\n\nWe are sorry to inform you that your order with ID " + order.getId() + " has been cancelled.\n\nOrder Details:\n\n";
        for (OrderItem item : order.getOrderItems()) {
            body += item.getProduct().getName() + " - " + item.getQuantity() + " - " + item.getPrice() + "\n";
        }
        body += "\nTotal Amount: " + order.getAmount() + "\n\nIf you have any questions or concerns, please feel free to contact us.\n\nBest regards,\nYour Order Team";

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(order.getUser().getEmail());
            helper.setSubject(subject);
            helper.setText(body);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
