package com.calendar.CalendarApplication.services;

import com.calendar.CalendarApplication.dtos.UserDto;
import com.calendar.CalendarApplication.entity.User;
import com.calendar.CalendarApplication.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
