package com.calendar.CalendarApplication.repository;

import com.calendar.CalendarApplication.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
