package com.learning.alertnotificationsevents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AlertNotificationsEventsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlertNotificationsEventsApplication.class, args);
    }

}
