package com.calendar.CalendarApplication.interfaces.event;

import com.calendar.CalendarApplication.dtos.event.CreateEventDto;
import com.calendar.CalendarApplication.dtos.event.GetEventsDto;
import com.calendar.CalendarApplication.dtos.event.UpdateEventDto;
import com.calendar.CalendarApplication.entity.Event;
import com.calendar.CalendarApplication.entity.User;

import java.util.List;
import java.util.Optional;

public interface EventServiceInterface {

    public Event createEvent(CreateEventDto event, User user);
    public List<GetEventsDto> findEventsByUserId(long userId);
    public Event updateEvent(UpdateEventDto eventToUpdate);
    public void deleteEvent(Event event);
}
