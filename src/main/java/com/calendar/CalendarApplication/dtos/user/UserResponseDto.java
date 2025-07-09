package com.calendar.CalendarApplication.dtos.user;

import java.time.LocalDate;

public record UserResponseDto(String username, String email, LocalDate birthDate) {
}
