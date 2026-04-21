package com.learning.alertnotificationsevents.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {

    private String userId;
    private String message;
    private String type;
    private  String priority;
}
