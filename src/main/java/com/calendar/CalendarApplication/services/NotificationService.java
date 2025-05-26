package com.calendar.CalendarApplication.services;

import com.calendar.CalendarApplication.dtos.notification.NotificationResponseDto;
import com.calendar.CalendarApplication.dtos.notification.NotificationToUpdateDto;
import com.calendar.CalendarApplication.entity.Event;
import com.calendar.CalendarApplication.entity.Notification;
import com.calendar.CalendarApplication.entity.User;
import com.calendar.CalendarApplication.interfaces.notification.NotificationServiceInterface;
import com.calendar.CalendarApplication.repository.NotificationRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class NotificationService implements NotificationServiceInterface {

    private NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(NotificationRepository notificationRepository, SimpMessagingTemplate messagingTemplate) {
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public Notification newUserNotification(User user){
        Notification notification = new Notification();
        notification.setTitle("Bem Vindo à sua lista de Eventos e Tarefas");
        notification.setDescription("Aqui você pode salvar seus eventos e tarefas que você tem marcados para servir de lembrete!");
        notification.setUserId(user);
        notification.setHasSeen(false);

        var savedNotification = notificationRepository.save(notification);

        return savedNotification;
    };

    public Notification newEventNotification(User user, Event event){
        Notification notification = new Notification();
        notification.setTitle("Seu evento " + event.getTitle() + " foi salvo com sucesso!");
        notification.setDescription("Seu evento " + event.getTitle() + " foi salvo com sucesso, notificaremos você quando estiver próximo da data!");
        notification.setUserId(user);
        notification.setEvent(event);
        notification.setHasSeen(false);

        var savedNotification = notificationRepository.save(notification);

        return savedNotification;
    };

    public Notification updateNotification(NotificationToUpdateDto notification){
        var savedNotification = notificationRepository.findById(notification.id());

        savedNotification.setTitle(notification.title());
        savedNotification.setDescription(notification.description());
        savedNotification.setHasSeen(notification.hasSeen());

        return notificationRepository.save(savedNotification);
    }

    public void sendNotificationToClients(Notification notification) {
        var dto = new NotificationResponseDto(
                notification.getTitle(),
                notification.getDescription(),
                notification.getHasSeen()
        );

        messagingTemplate.convertAndSend("/topic/notifications", dto);
    }

}
