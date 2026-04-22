package com.learning.alertnotificationsevents.kafka;

import com.learning.alertnotificationsevents.model.NotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DLQConsumer {



    @Autowired
    private NotificationProducer producer;

    private static final Logger log = LoggerFactory.getLogger(DLQConsumer.class);

    @KafkaListener(
            topics = "notification-events.DLQ", groupId = "dlq-processor-group"
    )
    public void consume(NotificationEvent event){
           log.info("Received the DLQ message : {} " , event);

           if( !event.getMessage().contains("fail")){
               return;
           }

           if(event.getCounter() >= 3) {
                  log.info("Permanent failure of the event processing");
                  return;
           }


            log.info("Retrying the DLQ message : {} " , event);
            event.setCounter(event.getCounter()+1);
            producer.send(event);




    }
}
