package com.learning.alertnotificationsevents.kafka;

import com.learning.alertnotificationsevents.model.NotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumer {

    private static final Logger log = LoggerFactory.getLogger(EmailConsumer.class);

    @KafkaListener(topics = "notification-events",groupId = "email-group")
    public void consume(NotificationEvent event){
           if(!"EMAIL".equals(event.getType())){
               return;
           }

           log.info("Email sent successfully");

    }
}
