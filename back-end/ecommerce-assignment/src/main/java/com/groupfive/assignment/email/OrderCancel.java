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
import org.springframework.stereotype.Service;

@Service
public class OrderCancel {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private OrderRepository orderRepository;

    public void sendCancelEmail(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found"));
        String subject = "Order Cancellation Confirmation";
        String greeting = "Dear " + order.getUser().getFirstname() + ",";
        String body = "<html><body><p>We are sorry to inform you that your order with ID <b>" + order.getId() + "</b> has been cancelled.</p>";
        body += "<p>Here are the details of your cancelled order:</p><ul>";
        for (OrderItem item : order.getOrderItems()) {
            body += "<li>" + item.getProduct().getName() + " - " + item.getQuantity() + " - Rs " + item.getPrice() + "</li>";
        }
        body += "</ul><p>Total Amount: <b>Rs " + order.getAmount() + "</b></p>";
        body += "<p>If you have any questions or concerns, please feel free to contact us.</p>";
        body += "<p>Best regards,<br>Your Order Team</p></body></html>";

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(order.getUser().getEmail());
            helper.setSubject(subject);
            helper.setText(greeting + "<br><br>" + body, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}
