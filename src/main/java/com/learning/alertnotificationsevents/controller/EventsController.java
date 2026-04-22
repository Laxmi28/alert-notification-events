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

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class EventsController {

    private final NotificationProducer producer;

    public EventsController(NotificationProducer producer){
        this.producer = producer;
    }

    @PostMapping(headers = "X-API-VERSION=1")
    public ResponseEntity<String> sendNotificationsV1(@RequestBody NotificationRequest request){

        NotificationEvent event = NotificationEvent.builder()
                                         .type(request.getType())
                                         .message(request.getMessage())
                                         .userId(request.getUserId()).build();
        producer.send(event);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);


    }

    @PostMapping(headers = "X-API-VERSION=2")
    public ResponseEntity<Map<String,Object>> sendNotificationsV2(@RequestBody NotificationRequest request){

        NotificationEvent event = NotificationEvent.builder()
                                    .type(request.getType())
                                    .message(request.getMessage())
                                    .userId(request.getUserId())
                                    .priority(request.getPriority())
                                    .build();

        producer.send(event);

        Map<String,Object> response = new HashMap<>();
        response.put("status","ACCEPTED");
        response.put("version","V2");
        response.put("priority",request.getPriority());


        return new ResponseEntity<>(response,HttpStatus.OK);

    }


}
