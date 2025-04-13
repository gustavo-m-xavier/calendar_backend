package com.calendar.CalendarApplication.dtos.event;

import com.calendar.CalendarApplication.entity.Event;

import java.util.Date;


public record GetEventsDto(long id, Event.EventType eventType, String title, String description, Date date, boolean is_completed) {
}
