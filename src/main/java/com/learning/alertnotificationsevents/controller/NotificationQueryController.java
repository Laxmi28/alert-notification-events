package com.learning.alertnotificationsevents.controller;

import com.learning.alertnotificationsevents.model.NotificationEntity;
import com.learning.alertnotificationsevents.repository.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
                                                     @RequestParam(required = false) String message,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "5") int size){

        Pageable pageable =  PageRequest.of(page,size, Sort.by("createdAt").descending());

        if( status != null && message!= null){
            return repository.findByUserIdAndStatusAndMessageContaining(userId,status,message,pageable);
        }else if (status != null){
            return  repository.findByUserIdAndStatus(userId,status,pageable);
        } else if (message != null) {
            return  repository.findByUserIdAndMessageContaining(userId,message,pageable);
        }
        return repository.findByUserId(userId,pageable);
    }

    @GetMapping("/priority/notifications")
    public Page<NotificationEntity> getHighPriorityNotifications(@RequestParam(name = "userId") String userId,
                                                                 @RequestParam(defaultValue = "0") int size,
                                                                 @RequestParam(defaultValue = "5") int page){

        Pageable pageable = PageRequest.of(size,page,Sort.by("createdAt").descending());

        return repository.findUserByHighPriority(userId,pageable);

    }
}
