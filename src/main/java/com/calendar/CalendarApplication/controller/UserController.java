package com.calendar.CalendarApplication.controller;

import com.calendar.CalendarApplication.dtos.LoginDto;
import com.calendar.CalendarApplication.dtos.LoginResponseDto;
import com.calendar.CalendarApplication.dtos.UserDto;
import com.calendar.CalendarApplication.dtos.UserResponseDto;
import com.calendar.CalendarApplication.entity.User;
import com.calendar.CalendarApplication.services.UserService;
import com.calendar.CalendarApplication.utils.DotenvUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.calendar.CalendarApplication.utils.JwtUtil;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserDto user) {
        try {
            var createdUser = userService.CreateUser(user);

            if (createdUser != null) {
                UserResponseDto userResponse = new UserResponseDto(createdUser.getUsername(), createdUser.getEmail(), createdUser.getBirthDate());

                return ResponseEntity.status(201)
                        .body(Map.of("message", "Usuário criado com sucesso!", "user", userResponse));
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

    @PostMapping("/login")
    public ResponseEntity<?> authUser(@RequestBody LoginDto loginDto) {

        try{

            String token = userService.authenticateUser(loginDto.email(), loginDto.password());

            if(token != null) {
                Optional<User> authUser = userService.getUser(loginDto.email(), loginDto.password());

                LoginResponseDto authResponse = new LoginResponseDto(
                        authUser.get().getUsername(),
                        authUser.get().getEmail(),
                        authUser.get().getBirthDate(),
                        token
                );

                return ResponseEntity.status(200).body(
                        Map.of(
                                "message", "Login realizado com sucesso",
                                "user", authResponse));
            } else {
                return ResponseEntity.status(401)
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
            @RequestBody UserDto user
//            @RequestHeader("Authorization") String token
    ) {

//        if(!jwtUtil.validateToken(token, user.username())){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }

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

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody String email) {

        try {
            var response = userService.deleteUser(email);

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
