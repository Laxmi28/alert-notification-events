package com.learning.alertnotificationsevents.repository;

import com.learning.alertnotificationsevents.model.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity,Long> {

    Page<NotificationEntity> findByUserId(String userId,Pageable pageable);

    Page<NotificationEntity> findByUserIdAndStatus(String userId, String status  , Pageable pageable);
}
