package com.learning.alertnotificationsevents.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent {

    private String userId;
    private String message;
    private String type;
    private  String priority;
}
