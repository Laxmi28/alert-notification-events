package com.learning.alertnotificationsevents.kafka;

import com.learning.alertnotificationsevents.model.NotificationEvent;
import com.learning.alertnotificationsevents.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RetryConsumer {

    private EmailService service;

    private static final Logger log = LoggerFactory.getLogger(RetryConsumer.class);

    @KafkaListener(topics = "notification-retry-topic",groupId = "retry-group")
    public void consume(NotificationEvent event){
           log.info("retrying send mail functionality");
           service.sendMail(event);

    }
}
