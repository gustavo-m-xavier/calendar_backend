package com.calendar.CalendarApplication.dtos.event;

import com.calendar.CalendarApplication.entity.Event;

import java.util.Date;

public record CreateEventDto(int user_id, Event.EventType eventType, String title, String description, Date date) {
}
