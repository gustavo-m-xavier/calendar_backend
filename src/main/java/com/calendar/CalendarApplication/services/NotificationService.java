package com.calendar.CalendarApplication.services;

import com.calendar.CalendarApplication.entity.Notification;
import com.calendar.CalendarApplication.entity.User;
import com.calendar.CalendarApplication.interfaces.notification.NotificationServiceInterface;
import com.calendar.CalendarApplication.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService implements NotificationServiceInterface {

    private NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
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

}
