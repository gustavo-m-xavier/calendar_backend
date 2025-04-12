package com.calendar.CalendarApplication.interfaces.notification;

import com.calendar.CalendarApplication.entity.Notification;
import com.calendar.CalendarApplication.entity.User;

public interface NotificationServiceInterface {

    Notification newUserNotification(User user);
}
