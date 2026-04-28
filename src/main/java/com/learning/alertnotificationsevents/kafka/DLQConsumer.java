package com.learning.alertnotificationsevents.kafka;

import com.learning.alertnotificationsevents.model.NotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class DLQConsumer {



    @Autowired
    private NotificationProducer producer;

    private static final Logger log = LoggerFactory.getLogger(DLQConsumer.class);

    @KafkaListener(
            topics = "notification-events.DLQ", groupId = "dlq-processor-group"
    )
    public void consume(NotificationEvent event){
           log.info("Received the DLQ message for event id : {} " , event.getId());

           if( !event.getMessage().contains("fail")){
               return;
           }

           if(event.getRetryCount() >= 3) {
                  log.info("Permanent failure of the event processing for event id : {}", event.getId());
                  return;
           }


            log.info("Retrying the DLQ message for event id : {} " , event.getId());
            event.setRetryCount(event.getRetryCount()+1);
            producer.send(event);




    }
}
