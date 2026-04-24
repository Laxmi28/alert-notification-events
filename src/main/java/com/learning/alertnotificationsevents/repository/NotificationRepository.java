package com.learning.alertnotificationsevents.repository;

import com.learning.alertnotificationsevents.model.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity,Long> {

    Page<NotificationEntity> findByUserId(String userId,Pageable pageable);

    Page<NotificationEntity> findByUserIdAndStatus(String userId, String status  , Pageable pageable);

    Page<NotificationEntity> findByUserIdAndStatusAndMessageContaining(String userId, String status , String message , Pageable pageable);

    Page<NotificationEntity> findByUserIdAndMessageContaining(String userId, String message , Pageable pageable);

    @Query("select n from NotificationEntity n where n.userId = :userId and n.priority = 'HIGH'")
    Page<NotificationEntity> findUserByHighPriority(@Param(value = "userId") String userId , Pageable pageable);
}
