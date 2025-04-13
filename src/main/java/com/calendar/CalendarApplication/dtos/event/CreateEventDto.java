package com.calendar.CalendarApplication.dtos.event;

import com.calendar.CalendarApplication.entity.Event;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record CreateEventDto(

        @NotNull
        @NotEmpty int user_id,

        @NotNull
        @NotEmpty Event.EventType eventType,

        @NotNull
        @NotEmpty String title,

        @NotNull
        @NotEmpty String description,

        @NotNull
        @NotEmpty Date date
) {}
