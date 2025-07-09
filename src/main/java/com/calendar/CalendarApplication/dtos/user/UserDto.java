package com.calendar.CalendarApplication.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;

public record UserDto(

        @NotNull
        @NotEmpty String username,

        @NotNull @Email
        @NotEmpty String email,

        @NotNull
        @NotEmpty String password,

        @NotNull
        @NotEmpty LocalDate birth_date
) {

    @Override
    public String username() {
        return username;
    }

    @Override
    public String email() {
        return email;
    }

    @Override
    public String password() {
        return password;
    }

    @Override
    public LocalDate birth_date() {
        return birth_date;
    }
}