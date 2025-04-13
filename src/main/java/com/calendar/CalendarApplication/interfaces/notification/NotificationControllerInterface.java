package com.calendar.CalendarApplication.interfaces.notification;

import com.calendar.CalendarApplication.dtos.notification.NotificationToUpdateDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface NotificationControllerInterface {
    ResponseEntity<?> updateNotification(
            @RequestBody NotificationToUpdateDto notificationToUpdateDto,
            @RequestHeader("Authorization") String authorizationHeader
    );
}
