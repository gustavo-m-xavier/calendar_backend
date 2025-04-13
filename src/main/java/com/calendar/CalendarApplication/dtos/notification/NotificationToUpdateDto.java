package com.calendar.CalendarApplication.dtos.notification;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NotificationToUpdateDto(
        @NotNull
        @NotEmpty long id,

        @NotNull
        @NotEmpty String title,

        @NotNull
        @NotEmpty String description,

        @NotNull
        @NotEmpty boolean hasSeen
) {
}
