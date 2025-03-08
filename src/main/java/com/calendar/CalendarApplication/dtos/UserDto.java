package com.calendar.CalendarApplication.dtos;

import java.util.Date;

public record UserDto(String username, String email, String password, Date birth_date) {
}
