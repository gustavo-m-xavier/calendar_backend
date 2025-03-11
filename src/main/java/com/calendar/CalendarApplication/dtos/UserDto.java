package com.calendar.CalendarApplication.dtos;

import java.util.Date;

public record UserDto(String username, String email, String password, Date birth_date) {

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
    public Date birth_date() {
        return birth_date;
    }
}