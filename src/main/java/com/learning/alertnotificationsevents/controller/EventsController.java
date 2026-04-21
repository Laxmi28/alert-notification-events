package com.learning.alertnotificationsevents.controller;

import com.learning.alertnotificationsevents.kafka.NotificationProducer;
import com.learning.alertnotificationsevents.model.NotificationEvent;
import com.learning.alertnotificationsevents.model.NotificationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
public class EventsController {

    private final NotificationProducer producer;

    public EventsController(NotificationProducer producer){
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<String> sendNotifications(@RequestBody NotificationRequest request){

        NotificationEvent event = new NotificationEvent(
                request.getUserId(), request.getMessage(), request.getType(), null
        );

        producer.send(event);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);


    }


}
