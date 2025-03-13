package com.calendar.CalendarApplication.dtos;

import java.util.Date;

public record LoginResponseDto(int id, String username, String email, Date birthDate, String token) {
}
