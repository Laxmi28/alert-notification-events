package com.learning.alertnotificationsevents.kafka;

import com.learning.alertnotificationsevents.model.NotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AuditConsumer {

    private static final Logger log = LoggerFactory.getLogger(AuditConsumer.class);

    @KafkaListener(topics = "notification-events", groupId = "audit-group")
    public void consume(NotificationEvent event){
        log.info("Audit done successfully");
    }
}
