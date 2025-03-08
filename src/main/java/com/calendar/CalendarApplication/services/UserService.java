package com.calendar.CalendarApplication.services;

import com.calendar.CalendarApplication.dtos.UserDto;
import com.calendar.CalendarApplication.entity.User;
import com.calendar.CalendarApplication.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

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

        var entity = new User(userDto.username(), userDto.email(), userDto.password(), userDto.birth_date());

        var savedUser =  userRepository.save(entity);

        return savedUser;
    }

    public Optional<User> getUser(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}
