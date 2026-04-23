package com.learning.alertnotificationsevents.kafka;

import com.learning.alertnotificationsevents.model.NotificationEntity;
import com.learning.alertnotificationsevents.model.NotificationEvent;
import com.learning.alertnotificationsevents.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailConsumer {

    private static final Logger log = LoggerFactory.getLogger(EmailConsumer.class);

    private final NotificationRepository repository;

    public EmailConsumer(NotificationRepository repository){
        this.repository = repository;
    }

    @KafkaListener(topics = "notification-events",groupId = "email-group")
    public void consume(NotificationEvent event){
           if(!"EMAIL".equals(event.getType())){
               return;
           }


            NotificationEntity entity = new NotificationEntity();
            entity.setUserId(event.getUserId());
            entity.setMessage(event.getMessage());
            entity.setType(event.getType());
            entity.setRetryCount(event.getRetryCount());
            entity.setCreatedAt(LocalDateTime.now());

         try{
             if(event.getMessage().contains("fail")){
                 throw new RuntimeException("The message consumption failed");
             }
             log.info("Email sent ");
             entity.setStatus("SENT");
         }catch(Exception ex){
             entity.setStatus("FAILED");
             repository.save(entity);

             throw  ex;

         }

           repository.save(entity);
           log.info("Email sent successfully");

    }
}
