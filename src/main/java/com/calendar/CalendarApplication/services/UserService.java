package com.calendar.CalendarApplication.services;

import com.calendar.CalendarApplication.dtos.UserDto;
import com.calendar.CalendarApplication.entity.User;
import com.calendar.CalendarApplication.repository.UserRepository;
import com.calendar.CalendarApplication.utils.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service
public class UserService {

    private JwtUtil jwtUtil;
    private UserRepository userRepository;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public User CreateUser(UserDto userDto) {

        var existingUser = getUser(userDto.email(), userDto.password());

        if(existingUser.isPresent()) {
            return existingUser.get();
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(userDto.password());

        var entity = new User(userDto.username(), userDto.email(), hashedPassword, userDto.birth_date());

        var savedUser =  userRepository.save(entity);

        return savedUser;
    }

    public Optional<User> getUser(String email, String password) {

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            return Optional.empty();
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        } else {
            return Optional.empty();
        }
    }

    public String authenticateUser(String email, String password) {
        Optional<User> user = getUser(email, password);
        if (user.isPresent()) {
            return jwtUtil.generateToken(user.get().getUsername());
        }else {
            return null;
        }
    }

    public User updateUser(UserDto user) {

        var userToUpdate = userRepository
                                .findByEmail(user.email())
                                .orElseThrow(() -> new RuntimeException("Usuário Não Encontrado"));

        if (
                !user.username().equals(userToUpdate.getUsername()) &&
                !userRepository.existsByUsername(user.username())
        ) {
            userToUpdate.setUsername(user.username());
        } else if(
                !user.email().equals(userToUpdate.getEmail()) &&
                !userRepository.existsByEmail(user.email())
        ) {
            userToUpdate.setEmail(user.email());
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.password());

        userToUpdate.setPassword(hashedPassword);
        userToUpdate.setBirthDate(user.birth_date());

        return userRepository.save(userToUpdate);

    }

    public String deleteUser(String email) {
        var user = userRepository.findByEmail(email);
        if(user.isPresent()) {
            userRepository.delete(user.get());
            return "Usuário deletado com sucesso";
        } else {
            return "Usuário não encontrado";
        }
    }
}
