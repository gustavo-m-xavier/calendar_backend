package com.calendar.CalendarApplication.controller;

import com.calendar.CalendarApplication.dtos.event.CreateEventDto;
import com.calendar.CalendarApplication.dtos.event.GetEventsDto;
import com.calendar.CalendarApplication.dtos.event.UpdateEventDto;
import com.calendar.CalendarApplication.entity.Notification;
import com.calendar.CalendarApplication.interfaces.event.EventControllerInterface;
import com.calendar.CalendarApplication.repository.EventRepository;
import com.calendar.CalendarApplication.repository.UserRepository;
import com.calendar.CalendarApplication.services.EventService;
import com.calendar.CalendarApplication.services.NotificationService;
import com.calendar.CalendarApplication.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events")
public class EventController implements EventControllerInterface {

    private final UserRepository userRepository;
    private JwtUtil jwtUtil;
    private EventService eventService;
    private EventRepository eventRepository;
    private NotificationService notificationService;

    public EventController(
            JwtUtil jwtUtil,
            EventService eventService,
            EventRepository eventRepository,
            UserRepository userRepository,
            NotificationService notificationService
    ) {
        this.eventRepository = eventRepository;
        this.eventService = eventService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createEvent(
            @RequestBody CreateEventDto eventDto,
            @RequestHeader("Authorization") String authorizationHeader
    ) {

        if(!authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(403).body(
                    Map.of(
                            "Erro", "Bearer token errado ou nao definido"
                    )
            );
        }

        var token = authorizationHeader.substring(7, authorizationHeader.length());
        var user = userRepository.findById(eventDto.user_id());

        if(!jwtUtil.validateToken(token, user.get().getUsername())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado");
        }

        try {

            var createdEvent = eventService.createEvent(eventDto, user.get());

            if(createdEvent != null) {

                Notification createdNotification = notificationService.newEventNotification(user.get(), createdEvent);

                notificationService.sendNotificationToUser(createdNotification, user);

                return ResponseEntity
                        .status(201)
                        .body(
                                Map.of(
                                        "message", "Evento criado com sucesso!",
                                        "evento:", createdEvent
                                )
                        );
            } else {
                return ResponseEntity
                        .status(400)
                        .body(
                                Map.of(
                                        "message", "Erro ao criar evento, usuário não encontrado."
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

    @PostMapping("/get/{userId}")
    public ResponseEntity<?> getEvent(
            @PathVariable long userId,
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
        var user = userRepository.findById((int)userId);

        if(!jwtUtil.validateToken(token, user.get().getUsername())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado");
        }

        try {

            List<GetEventsDto> userEvents = eventService.findEventsByUserId(userId);

            if(userEvents != null) {

                return ResponseEntity
                        .status(200)
                        .body(
                                Map.of(
                                        "events", userEvents
                                )
                        );

            } else {
                return ResponseEntity
                        .status(404)
                        .body(
                                Map.of(
                                        "message", "Não foram encontrados eventos desse usuário"
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

    @PutMapping("/update")
    public ResponseEntity<?> updateEvent(
            @RequestBody UpdateEventDto updateEventDto,
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
        var user = userRepository.findById((int)updateEventDto.userId());

        if(!jwtUtil.validateToken(token, user.get().getUsername())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado");
        }

        try{

            var updatedEvent = eventService.updateEvent(updateEventDto);

            if(updatedEvent != null) {
                return ResponseEntity
                        .status(200)
                        .body(
                                Map.of(
                                        "Message", "Evento atualizado com sucesso!",
                                        "evento:", updatedEvent
                                )
                        );
            } else {
                return ResponseEntity
                        .status(404)
                        .body(
                                Map.of(
                                        "Message", "Evento não encontrado"
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

    @DeleteMapping("/delete/{eventId}")
    public ResponseEntity<?> deleteEvent(
            @PathVariable long eventId,
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

        try {

            var event = eventRepository.findById(eventId);

            if(event != null) {

                var user = event.getUser();

                if(!jwtUtil.validateToken(token, user.getUsername())){
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado");
                }

                eventService.deleteEvent(event);

                return ResponseEntity
                        .status(200)
                        .body(
                                Map.of(
                                        "message", "Evento deletado com sucesso!"
                                )
                        );
            } else {
                return ResponseEntity
                        .status(404)
                        .body(
                                Map.of(
                                        "message", "Evento não encontrado"
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
