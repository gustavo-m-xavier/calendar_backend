package com.calendar.CalendarApplication.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LoginDto(
        @NotNull @Email
        @NotEmpty String email,

        @NotNull
        @NotEmpty String password
) {
}
