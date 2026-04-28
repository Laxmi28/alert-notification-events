package com.learning.alertnotificationsevents.kafka;

import com.learning.alertnotificationsevents.model.NotificationEntity;
import com.learning.alertnotificationsevents.model.NotificationEvent;
import com.learning.alertnotificationsevents.repository.NotificationRepository;
import com.learning.alertnotificationsevents.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailConsumer {

    private static final Logger log = LoggerFactory.getLogger(EmailConsumer.class);

    private final NotificationRepository repository;

    private final EmailService emailService;

    public EmailConsumer(NotificationRepository repository,EmailService emailService){
        this.repository = repository;
        this.emailService = emailService;
    }

    @KafkaListener(topics = "notification-events",groupId = "email-group")
    public void consume(NotificationEvent event){
           if(!"EMAIL".equals(event.getType())){
               return;
           }
        emailService.sendMail(event);




    }
}
