package com.calendar.CalendarApplication.controller;

import com.calendar.CalendarApplication.dtos.notification.NotificationResponseDto;
import com.calendar.CalendarApplication.dtos.notification.NotificationToUpdateDto;
import com.calendar.CalendarApplication.interfaces.notification.NotificationControllerInterface;
import com.calendar.CalendarApplication.repository.NotificationRepository;
import com.calendar.CalendarApplication.services.NotificationService;
import com.calendar.CalendarApplication.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/notification")
public class NotificationController implements NotificationControllerInterface {

    private JwtUtil jwtUtil;
    private NotificationService notificationService;
    private NotificationRepository notificationRepository;

    public NotificationController(
            JwtUtil jwtUtil,
            NotificationService notificationService,
            NotificationRepository notificationRepository
    ) {
        this.jwtUtil = jwtUtil;
        this.notificationService = notificationService;
        this.notificationRepository = notificationRepository;
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateNotification(
            @RequestBody NotificationToUpdateDto notificationToUpdateDto,
            @RequestHeader("Authorization") String authorizationHeader
    ){
        if(!authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(403).body(
                    Map.of(
                            "Erro", "Bearer token errado ou nao definido"
                    )
            );
        }

        var token = authorizationHeader.substring(7, authorizationHeader.length());
        var user = notificationRepository.findUserByNotificationId(notificationToUpdateDto.id());

        if(!jwtUtil.validateToken(token, user.getUsername())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado");
        }

        try {

            var notificationUpdated = notificationService.updateNotification(notificationToUpdateDto);

            if(notificationUpdated != null) {

                NotificationResponseDto notificationResponse = new NotificationResponseDto(
                        notificationUpdated.getTitle(),
                        notificationUpdated.getDescription(),
                        notificationUpdated.getHasSeen());

                return ResponseEntity
                        .status(200)
                        .body(
                                Map.of(
                                        "Message", "Notificação atualizada com sucesso!",
                                        "notificação:", notificationResponse
                                )
                        );
            } else {
                return ResponseEntity
                        .status(404)
                        .body(
                                Map.of(
                                        "Message", "Notificação não encontrada"
                                )
                        );
            }

        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .body(
                            Map.of(
                                    "message", "Ocorreu um erro interno ao processar a solicitação. Tente novamente mais tarde.",
                                    "errorDetail", e.getMessage()
                            )
                    );
        }
    }
}
