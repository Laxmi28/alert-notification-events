package com.learning.alertnotificationsevents.service;


import com.learning.alertnotificationsevents.model.NotificationEntity;
import com.learning.alertnotificationsevents.model.NotificationEvent;
import com.learning.alertnotificationsevents.repository.NotificationRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);


    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;
    private final NotificationRepository repository;

    public EmailService(KafkaTemplate<String, NotificationEvent> kafkaTemplate, NotificationRepository repository) {
        this.kafkaTemplate = kafkaTemplate;
        this.repository = repository;
    }


    @CircuitBreaker(name = "emailService", fallbackMethod = "fallback")
    public void sendMail(NotificationEvent event) {


        NotificationEntity entity = new NotificationEntity();
        entity.setUserId(event.getUserId());
        entity.setMessage(event.getMessage());
        entity.setType(event.getType());
        entity.setRetryCount(event.getRetryCount());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setPriority(event.getPriority());

        try {
            if (event.getMessage().contains("fail")) {
                throw new RuntimeException("The message consumption failed");
            }
            log.info("Email sent for event id : {} ", event.getId());
            entity.setStatus("SENT");
        } catch (Exception ex) {
            log.error("Email failed for event id : {} ", event.getId());
            entity.setStatus("FAILED");
            repository.save(entity);
            throw ex;

        }

        repository.save(entity);


        log.info("Email sent successfully");

    }

    public void fallback(NotificationEvent event, Exception ex) {
        log.debug("Email fallback called for event : {} ", event.getId());
        kafkaTemplate.send("notification-retry-topic", event);


    }
}
