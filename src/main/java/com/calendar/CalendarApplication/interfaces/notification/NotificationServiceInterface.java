package com.calendar.CalendarApplication.interfaces.notification;

import com.calendar.CalendarApplication.dtos.notification.NotificationToUpdateDto;
import com.calendar.CalendarApplication.entity.Event;
import com.calendar.CalendarApplication.entity.Notification;
import com.calendar.CalendarApplication.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationServiceInterface {

    Notification newUserNotification(User user);
    Notification newEventNotification(User user, Event event);
    Notification updateNotification(NotificationToUpdateDto notification);
    void sendNotificationToClients(Notification notification);
}
