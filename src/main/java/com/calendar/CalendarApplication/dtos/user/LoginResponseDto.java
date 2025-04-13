package com.calendar.CalendarApplication.dtos.user;

import java.util.Date;

public record LoginResponseDto(int id, String username, String email, Date birthDate) {
}
