package com.elsawang.microservices.notification.service;

import com.elsawang.microservices.notification.exception.EmailSendException;
import com.elsawang.microservices.order.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Service responsible for sending email notifications when orders are placed.
 * Listens to Kafka events and processes them to send formatted emails to customers.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${notification.template.order-confirmation.subject}")
    private String subjectTemplate;

    @Value("${notification.template.order-confirmation.body}")
    private String bodyTemplate;

    /**
     * Listens to order-placed events and sends confirmation emails to customers.
     * Implements retry mechanism for handling temporary email service failures.
     *
     * @param orderPlacedEvent The event containing order and customer details
     * @throws EmailSendException if email sending fails after retries
     */
    @KafkaListener(topics = "order-placed", groupId = "notification-group")
    @Retryable(maxAttempts = 3, value = MailException.class)
    public void handleOrderPlacedEvent(OrderPlacedEvent orderPlacedEvent) {
        log.info("Processing order notification for order: {}", orderPlacedEvent.getOrderNumber());
        
        validateEventData(orderPlacedEvent);
        
        MimeMessagePreparator messagePreparator = createEmailMessage(orderPlacedEvent);

        try {
            javaMailSender.send(messagePreparator);
            log.info("Order confirmation email sent successfully for order: {}", 
                    orderPlacedEvent.getOrderNumber());
        } catch (MailException e) {
            log.error("Failed to send order confirmation email for order: {}", 
                    orderPlacedEvent.getOrderNumber(), e);
            throw new EmailSendException("Failed to send order confirmation email", e);
        }
    }

    /**
     * Validates the required fields in the order event.
     * 
     * @param event The order event to validate
     * @throws IllegalArgumentException if required fields are missing
     */
    private void validateEventData(OrderPlacedEvent event) {
        Objects.requireNonNull(event, "Order event cannot be null");
        Objects.requireNonNull(event.getEmail(), "Customer email is required");
        Objects.requireNonNull(event.getOrderNumber(), "Order number is required");
    }

    /**
     * Creates an email message using the order event data and email templates.
     * 
     * @param event The order event containing customer and order details
     * @return A prepared email message
     */
    private MimeMessagePreparator createEmailMessage(OrderPlacedEvent event) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(event.getEmail());
            
            String subject = String.format(subjectTemplate, event.getOrderNumber());
            messageHelper.setSubject(subject);
            
            String body = String.format(bodyTemplate,
                    event.getFirstName(),
                    event.getLastName(),
                    event.getOrderNumber());
            messageHelper.setText(body, true); // true enables HTML content
        };
    }
}
