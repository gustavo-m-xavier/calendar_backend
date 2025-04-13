package com.calendar.CalendarApplication.interfaces.user;

import com.calendar.CalendarApplication.dtos.user.LoginDto;
import com.calendar.CalendarApplication.dtos.user.UpdateUserDto;
import com.calendar.CalendarApplication.dtos.user.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface UserControllerInterface {
    ResponseEntity<?> createUser(@RequestBody UserDto user);
    ResponseEntity<?> authUser(@RequestBody LoginDto loginDto);
    ResponseEntity<?> updateUser(
            @RequestBody UpdateUserDto user,
            @RequestHeader("Authorization") String token
    );
    ResponseEntity<?> deleteUser(
            @PathVariable int id,
            @RequestHeader("Authorization") String token
    );
}
