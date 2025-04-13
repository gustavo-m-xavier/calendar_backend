package com.calendar.CalendarApplication.repository;

import com.calendar.CalendarApplication.entity.Notification;
import com.calendar.CalendarApplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Notification findById(long id);
    @Query("SELECT n.user FROM Notification n WHERE n.id = :notificationId")
    User findUserByNotificationId(@Param("notificationId") long notificationId);
}
