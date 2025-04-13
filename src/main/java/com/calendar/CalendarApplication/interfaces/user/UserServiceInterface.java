package com.calendar.CalendarApplication.interfaces.user;

import com.calendar.CalendarApplication.dtos.user.UpdateUserDto;
import com.calendar.CalendarApplication.dtos.user.UserDto;
import com.calendar.CalendarApplication.entity.User;

import java.util.Optional;

public interface UserServiceInterface {
    User createUser(UserDto userDto);
    Optional<User> getUser(String email, String password);
    String authenticateUser(String email, String password);
    User updateUser(UpdateUserDto user);
    String deleteUser(int id);
}
