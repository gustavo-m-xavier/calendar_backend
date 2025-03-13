package com.calendar.CalendarApplication.interfaces;

import com.calendar.CalendarApplication.dtos.UpdateUserDto;
import com.calendar.CalendarApplication.dtos.UserDto;
import com.calendar.CalendarApplication.entity.User;

import java.util.Optional;

public interface UserServiceInterface {
    public User createUser(UserDto userDto);
    public Optional<User> getUser(String email, String password);
    public String authenticateUser(String email, String password);
    public User updateUser(UpdateUserDto user);
    public String deleteUser(int id);
}
