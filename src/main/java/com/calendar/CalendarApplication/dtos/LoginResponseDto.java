package com.calendar.CalendarApplication.dtos;

import java.util.Date;

public record LoginResponseDto( String username, String email, Date birthDate, String token) {
}
