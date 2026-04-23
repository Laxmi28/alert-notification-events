package com.learning.alertnotificationsevents.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationEvent {

    private String userId;
    private String message;
    private String type;
    private  String priority;
    private int retryCount;
}
