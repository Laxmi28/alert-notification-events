package com.learning.alertnotificationsevents.controller;

import com.learning.alertnotificationsevents.model.NotificationEntity;
import com.learning.alertnotificationsevents.repository.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class NotificationQueryController {

    private final NotificationRepository repository;

    public NotificationQueryController(NotificationRepository repository){
        this.repository = repository;
    }

    @GetMapping("/{userId}/notifications")
    public Page<NotificationEntity> getNotifications(@PathVariable String userId,
                                                     @RequestParam(required = false) String status,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "5") int size){

        if( status != null){
            return repository.findByUserIdAndStatus(userId,status,PageRequest.of(page,size));
        }
        return repository.findByUserId(userId, PageRequest.of(page,size));
    }
}
