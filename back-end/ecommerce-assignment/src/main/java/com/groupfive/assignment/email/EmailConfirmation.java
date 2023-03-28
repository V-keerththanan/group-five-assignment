package com.groupfive.assignment.email;

import com.groupfive.assignment.model.Order;
import com.groupfive.assignment.model.OrderItem;
import com.groupfive.assignment.repository.OrderRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Optional;

public class EmailConfirmation {
    @Autowired
    OrderRepository orderRepo;
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendConfirmationEmail(Long order_id,String email){
        Optional<Order> existingOrder=orderRepo.findById(order_id);
        if(!existingOrder.isPresent()){
            throw new EntityNotFoundException("Order not found with ID: " + order_id);
        }
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("Confirmation Email");
            helper.setText("<html><body>"
                    + "<h2>Thank you for placing your order with us. Your order has been received and is being processed.</h2>"
                    + "<table border=\"1\">"
                    + "<tr>"
                    + "<th>Product Name</th>"
                    + "<th>price of each product</th>"
                    + "<th>Quantity</th>"
                    + "<th>price of total</th>"
                    + "</tr>"
                    + getProductDetailsTable(order_id)
                    + "</table>"
                    + "</body></html>",true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }




        javaMailSender.send(message);

    }


    private String getProductDetailsTable(Long order_id) {
        StringBuilder sb = new StringBuilder();
        Optional<Order> existingOrder=orderRepo.findById(order_id);

        if (!existingOrder.isPresent()) {
            throw new EntityNotFoundException("Order not found with ID: " + order_id);
        }
        Order order=existingOrder.get();
        for (OrderItem item : order.getOrderItems()) {
            sb.append("<tr>")
                    .append("<td>").append(item.getProduct().getName()).append("</td>")
                    .append("<td>").append("Rs").append(item.getProduct().getPrice()).append("</td>")
                    .append("<td>").append(item.getQuantity()).append("</td>")
                    .append("<td>").append(item.getPrice()).append("</td>")
                    .append("</tr>");
        }
        return sb.toString();
    }

}
