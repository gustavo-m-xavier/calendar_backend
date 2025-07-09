package com.calendar.CalendarApplication.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;

public record UpdateUserDto(
        @NotNull
        @NotEmpty int id,

        @NotNull
        @NotEmpty String username,

        @NotNull @Email
        @NotEmpty String email,

        @NotNull
        @NotEmpty String password,

        @NotNull
        @NotEmpty LocalDate birthDate
) {}
