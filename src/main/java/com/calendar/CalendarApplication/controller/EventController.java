package com.calendar.CalendarApplication.controller;

import com.calendar.CalendarApplication.dtos.event.CreateEventDto;
import com.calendar.CalendarApplication.dtos.event.GetEventsDto;
import com.calendar.CalendarApplication.interfaces.event.EventControllerInterface;
import com.calendar.CalendarApplication.repository.EventRepository;
import com.calendar.CalendarApplication.repository.UserRepository;
import com.calendar.CalendarApplication.services.EventService;
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

    public EventController(JwtUtil jwtUtil, EventService eventService, EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.eventService = eventService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createEvent(
            @RequestBody CreateEventDto eventDto,
            @RequestHeader("Authorization") String token
    ) {

        var user = userRepository.findById(eventDto.user_id());

        if(!jwtUtil.validateToken(token, user.get().getUsername())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado");
        }

        try {

            var createdEvent = eventService.createEvent(eventDto, user.get());

            if(createdEvent != null) {
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
            return ResponseEntity.status(500)
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
            @RequestHeader("Authorization") String token
    ){
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
            return ResponseEntity.status(500)
                    .body(
                            Map.of(
                                    "message", "Ocorreu um erro interno ao processar a solicitação. Tente novamente mais tarde.",
                                    "errorDetail", e.getMessage()
                            )
                    );
        }
    }

}
