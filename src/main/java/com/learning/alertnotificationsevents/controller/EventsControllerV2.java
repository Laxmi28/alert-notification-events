package com.learning.alertnotificationsevents.controller;
import com.learning.alertnotificationsevents.kafka.NotificationProducer;
import com.learning.alertnotificationsevents.model.NotificationEvent;
import com.learning.alertnotificationsevents.model.NotificationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("/api/v2/notifications")
public class EventsControllerV2 {

    private final NotificationProducer producer;

    public EventsControllerV2(NotificationProducer producer){
        this.producer = producer;
    }
    @PostMapping
    public ResponseEntity<Map<String,Object>> sendNotifications(@RequestBody NotificationRequest request){

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
