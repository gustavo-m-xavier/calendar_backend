package com.calendar.CalendarApplication.interfaces.notification;

import com.calendar.CalendarApplication.dtos.notification.NotificationToUpdateDto;
import com.calendar.CalendarApplication.entity.Event;
import com.calendar.CalendarApplication.entity.Notification;
import com.calendar.CalendarApplication.entity.User;

import java.util.Optional;

public interface NotificationServiceInterface {

    Notification newUserNotification(User user);
    Notification newEventNotification(User user, Event event);
    Notification updateNotification(NotificationToUpdateDto notification);
    void sendNotificationToClients(Notification notification);
    void sendDueDateNotification(Event event, int hourTo, Optional<User> user);
}
