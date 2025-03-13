package com.calendar.CalendarApplication.dtos.user;

import java.util.Date;

public record UpdateUserDto(int id, String username, String email, String password, Date birthDate) {
}
