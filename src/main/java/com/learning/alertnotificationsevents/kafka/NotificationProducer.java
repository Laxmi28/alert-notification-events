package com.learning.alertnotificationsevents.kafka;

import com.learning.alertnotificationsevents.model.NotificationEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    private static  final String TOPIC = "notification-events";

    public NotificationProducer(KafkaTemplate<String, NotificationEvent> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(NotificationEvent event){
        kafkaTemplate.send(TOPIC,event.getType(),event);
    }



}
