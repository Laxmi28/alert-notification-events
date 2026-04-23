package com.learning.alertnotificationsevents.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="notifications")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationEntity {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String message;
    private String type;
    private String status;
    private Integer retryCount;
    private LocalDateTime createdAt;
}
