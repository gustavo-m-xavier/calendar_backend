package com.calendar.CalendarApplication.interfaces.user;

import com.calendar.CalendarApplication.dtos.user.LoginDto;
import com.calendar.CalendarApplication.dtos.user.UpdateUserDto;
import com.calendar.CalendarApplication.dtos.user.UserDto;
import com.calendar.CalendarApplication.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface UserControllerInterface {
    public ResponseEntity<?> createUser(@RequestBody UserDto user);
    public ResponseEntity<?> authUser(@RequestBody LoginDto loginDto);
    public ResponseEntity<?> updateUser(
            @RequestBody UpdateUserDto user,
            @RequestHeader("Authorization") String token
    );
    public ResponseEntity<?> deleteUser(
            @PathVariable int id,
            @RequestHeader("Authorization") String token
    );
}
