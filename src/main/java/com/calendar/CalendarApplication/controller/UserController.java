package com.calendar.CalendarApplication.controller;

import com.calendar.CalendarApplication.dtos.UserDto;
import com.calendar.CalendarApplication.entity.User;
import com.calendar.CalendarApplication.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserDto user) {
        try {
            var createdUser = userService.CreateUser(user);

            if (createdUser != null) {
                return ResponseEntity.status(201)
                        .body(Map.of("message", "Usuário criado com sucesso!", "user", createdUser));
            } else {
                return ResponseEntity.status(409)
                        .body(Map.of("message", "Erro: O usuário já existe. Tente com outro nome de usuário"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("message", "Ocorreu um erro interno ao processar a solicitação. Tente novamente mais tarde.",
                            "errorDetail", e.getMessage()));
        }
    }

    @GetMapping("/get")
    public ResponseEntity<User> getUser(@RequestParam String email, @RequestParam String password) {
        return null;
    }

    @PostMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody UserDto user) {
        return null;
    }

    @PostMapping("/delete")
    public ResponseEntity<User> deleteUser(@RequestParam String email) {
        return null;
    }
}
