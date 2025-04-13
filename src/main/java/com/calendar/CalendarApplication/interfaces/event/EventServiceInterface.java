package com.calendar.CalendarApplication.interfaces.event;

import com.calendar.CalendarApplication.dtos.event.CreateEventDto;
import com.calendar.CalendarApplication.dtos.event.GetEventsDto;
import com.calendar.CalendarApplication.dtos.event.UpdateEventDto;
import com.calendar.CalendarApplication.entity.Event;
import com.calendar.CalendarApplication.entity.User;

import java.util.List;

public interface EventServiceInterface {

    Event createEvent(CreateEventDto event, User user);
    List<GetEventsDto> findEventsByUserId(long userId);
    Event updateEvent(UpdateEventDto eventToUpdate);
    void deleteEvent(Event event);
}
