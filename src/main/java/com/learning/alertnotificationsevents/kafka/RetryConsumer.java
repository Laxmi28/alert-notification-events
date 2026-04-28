package com.learning.alertnotificationsevents.kafka;

import com.learning.alertnotificationsevents.model.NotificationEvent;
import com.learning.alertnotificationsevents.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RetryConsumer {

    @Autowired
    private EmailService service;
    @Autowired
    private KafkaTemplate<String,NotificationEvent> kafkaTemplate;

    private static final Logger log = LoggerFactory.getLogger(RetryConsumer.class);

    @KafkaListener(topics = "notification-retry-topic",groupId = "retry-group")
    public void retry(NotificationEvent event){

           if ( event.getRetryCount() >= 3){
               log.info("Permanent of the service no recovering handled!!");
               kafkaTemplate.send("notification-events.DLQ",event);
               return;
           }
           try{
               event.setRetryCount(event.getRetryCount() + 1);
               log.info("retrying send mail functionality");
               service.sendMail(event);
           }catch(Exception e){
               log.info("Retry failed again");
               kafkaTemplate.send("notification-retry-topic", event);

           }


    }
}
