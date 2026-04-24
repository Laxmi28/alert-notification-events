package com.learning.alertnotificationsevents.service;


import com.learning.alertnotificationsevents.model.NotificationEvent;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private int count = 0;

    @Autowired
    private KafkaTemplate<String,NotificationEvent> kafkaTemplate;

//    @CircuitBreaker(name="emailService",fallbackMethod = "emailFallBack")
    @Retry(name ="emailRetry")
    public void sendMail(NotificationEvent event){



        // just to test cirucit breaker pattern we are implementing  a failure scenario

        if(event.getMessage().contains("fail")){
            log.info("Email message sending failed");
            count++;

            log.info("counter increased by : {}" , count);

            throw new RuntimeException("Email sending failed!");
        }



        log.info("Email sent successfully");

    }

    public void emailFallBack(NotificationEvent event, Exception ex){
         log.info("Email fallback called");

         kafkaTemplate.send("notification-retry-topic",event);




    }
}
