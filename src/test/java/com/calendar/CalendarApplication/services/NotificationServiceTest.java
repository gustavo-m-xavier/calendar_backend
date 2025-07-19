package com.calendar.CalendarApplication.services;

import com.calendar.CalendarApplication.entity.User;
import com.calendar.CalendarApplication.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @InjectMocks
    NotificationService notificationService;

    @Mock
    NotificationRepository notificationRepository;

    @Mock
    SimpMessagingTemplate messagingTemplate;

    @Test
    void newUserNotification() {
//        Mockito.when(notificationRepository.findById(Mockito.anyLong())).thenReturn();
//        notificationService.newUserNotification(new User());
    }

    @Test
    void newEventNotification() {
    }
}