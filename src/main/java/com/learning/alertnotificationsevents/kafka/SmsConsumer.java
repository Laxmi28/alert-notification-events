package com.learning.alertnotificationsevents.kafka;

import com.learning.alertnotificationsevents.model.NotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SmsConsumer {

    private static final Logger log = LoggerFactory.getLogger(EmailConsumer.class);

    @KafkaListener(topics = "notification-events", groupId = "sms-group")
    public  void consume(NotificationEvent event){
        if(!"SMS".equals(event.getType())){
            return;
        }

        log.info("SMS sent successfully");
    }
}
