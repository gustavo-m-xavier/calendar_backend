package com.calendar.CalendarApplication.interfaces.user;

import com.calendar.CalendarApplication.dtos.user.UpdateUserDto;
import com.calendar.CalendarApplication.dtos.user.UserDto;
import com.calendar.CalendarApplication.entity.User;

import java.util.Optional;

public interface UserServiceInterface {
    public User createUser(UserDto userDto);
    public Optional<User> getUser(String email, String password);
    public String authenticateUser(String email, String password);
    public User updateUser(UpdateUserDto user);
    public String deleteUser(int id);
}
