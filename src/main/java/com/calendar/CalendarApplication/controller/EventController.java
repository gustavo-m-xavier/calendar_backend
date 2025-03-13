package com.calendar.CalendarApplication.controller;

import com.calendar.CalendarApplication.interfaces.event.EventControllerInterface;
import com.calendar.CalendarApplication.repository.EventRepository;
import com.calendar.CalendarApplication.services.EventService;
import com.calendar.CalendarApplication.utils.JwtUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class EventController implements EventControllerInterface {

    private JwtUtil jwtUtil;
    private EventService eventService;
    private EventRepository eventRepository;

    public EventController(JwtUtil jwtUtil, EventService eventService, EventRepository eventRepository) {
        this.eventRepository = eventRepository;
        this.eventService = eventService;
        this.jwtUtil = jwtUtil;
    }

}
