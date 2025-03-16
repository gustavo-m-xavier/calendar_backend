package com.calendar.CalendarApplication.services;

import com.calendar.CalendarApplication.dtos.event.CreateEventDto;
import com.calendar.CalendarApplication.entity.Event;
import com.calendar.CalendarApplication.entity.User;
import com.calendar.CalendarApplication.interfaces.event.EventServiceInterface;
import com.calendar.CalendarApplication.repository.EventRepository;
import com.calendar.CalendarApplication.repository.UserRepository;
import com.calendar.CalendarApplication.utils.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventService implements EventServiceInterface {

    private JwtUtil jwtUtil;
    private EventRepository eventRepository;
    private UserRepository userRepository;

    public EventService(JwtUtil jwtUtil, EventRepository eventRepository) {
        this.jwtUtil = jwtUtil;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public Event createEvent(CreateEventDto event, User user) {

        if(user != null) {
            var entity = new Event(user, event.eventType(), event.title(), event.description(), event.date());

            var savedEvent = eventRepository.save(entity);

            return savedEvent;
        } else {
            return null;
        }
    }
}
