package com.calendar.CalendarApplication.interfaces.event;

import com.calendar.CalendarApplication.dtos.event.CreateEventDto;
import com.calendar.CalendarApplication.entity.Event;
import com.calendar.CalendarApplication.entity.User;

import java.util.Optional;

public interface EventServiceInterface {

    public Event createEvent(CreateEventDto event, User user);
}
