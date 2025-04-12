package com.calendar.CalendarApplication.controller;

import com.calendar.CalendarApplication.dtos.event.GetEventsDto;
import com.calendar.CalendarApplication.dtos.user.*;
import com.calendar.CalendarApplication.entity.Notification;
import com.calendar.CalendarApplication.entity.User;
import com.calendar.CalendarApplication.interfaces.user.UserControllerInterface;
import com.calendar.CalendarApplication.repository.UserRepository;
import com.calendar.CalendarApplication.services.EventService;
import com.calendar.CalendarApplication.services.NotificationService;
import com.calendar.CalendarApplication.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.calendar.CalendarApplication.utils.JwtUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController implements UserControllerInterface {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private EventService eventService;
    private NotificationService notificationService;

    public UserController(UserService userService, JwtUtil jwtUtil, UserRepository userRepository, EventService eventService, NotificationService notificationService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.eventService = eventService;
        this.notificationService = notificationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserDto user) {
        try {
            var createdUser = userService.createUser(user);

            if (createdUser != null) {
                UserResponseDto userResponse = new UserResponseDto(createdUser.getUsername(), createdUser.getEmail(), createdUser.getBirthDate());

                Notification createdNotification = notificationService.newUserNotification(createdUser);

                return ResponseEntity
                        .status(201)
                        .body(
                                Map.of(
                                        "message", "Usuário criado com sucesso!",
                                        "user", userResponse,
                                        "notification", createdNotification
                                )
                        );
            } else {
                return ResponseEntity
                        .status(409)
                        .body(
                                Map.of(
                                        "message", "Erro: Este nome de usuário já existe. Tente com outro nome de usuário"
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

    @PostMapping("/login")
    public ResponseEntity<?> authUser(@RequestBody LoginDto loginDto) {

        try{

            String token = userService.authenticateUser(loginDto.email(), loginDto.password());

            if(token != null) {
                Optional<User> authUser = userService.getUser(loginDto.email(), loginDto.password());

                LoginResponseDto authResponse = new LoginResponseDto(
                        authUser.get().getId(),
                        authUser.get().getUsername(),
                        authUser.get().getEmail(),
                        authUser.get().getBirthDate()
                );

                List<GetEventsDto> userEvents = eventService.findEventsByUserId(authUser.get().getId());

                LinkedHashMap<String, Object> response = new LinkedHashMap<>();
                response.put("message", "Login realizado com sucesso");
                response.put("user", authResponse);
                response.put("user_events", userEvents);

                return ResponseEntity
                        .status(200)
                        .header("Authorization", "Bearer " + token)
                        .body(response);
            } else {
                return ResponseEntity
                        .status(401)
                        .body(Map.of("message", "E-mail ou senha inválidos"));
            }

        }catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of(
                            "message", "Ocorreu um erro no login, tente novamente mais tarde.",
                            "errorDetail", e.getMessage()
                    )
            );
        }

    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(
            @RequestBody UpdateUserDto user,
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
        var userWithoutChanges = userRepository.findById(user.id());

        if(!jwtUtil.validateToken(token, userWithoutChanges.get().getUsername())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado");
        }

        try {
            var updatedUser = userService.updateUser(user);

            return ResponseEntity.status(200).body(
                    Map.of(
                            "message", "Usuário atualizado com sucesso!",
                            "user", updatedUser
                    )
            );

        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of(
                            "message", "Ocorreu um erro enquanto atualizava o usuário.",
                            "errorDetail", e.getMessage()
                    )
            );
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable int id,
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
        var existingUser = userRepository.findById(id);
        if(!jwtUtil.validateToken(token, existingUser.get().getUsername())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            var response = userService.deleteUser(id);

            if(response.contains("deletado")){
                return ResponseEntity.status(200).body(
                        response
                );
            } else {
                return ResponseEntity.status(404).body(response);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of(
                            "message", "Ocorreu um erro enquanto deletava o usuário.",
                            "errorDetail", e.getMessage()
                    )
            );
        }
    }
}
