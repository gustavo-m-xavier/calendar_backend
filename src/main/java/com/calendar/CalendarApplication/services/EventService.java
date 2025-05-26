package com.calendar.CalendarApplication.services;

import com.calendar.CalendarApplication.dtos.event.CreateEventDto;
import com.calendar.CalendarApplication.dtos.event.GetEventsDto;
import com.calendar.CalendarApplication.dtos.event.UpdateEventDto;
import com.calendar.CalendarApplication.entity.Event;
import com.calendar.CalendarApplication.entity.User;
import com.calendar.CalendarApplication.interfaces.event.EventServiceInterface;
import com.calendar.CalendarApplication.repository.EventRepository;
import com.calendar.CalendarApplication.repository.UserRepository;
import com.calendar.CalendarApplication.utils.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<GetEventsDto> findEventsByUserId(long userId) {
        List<Event> events = eventRepository.findByUserId(userId);

        return events.stream()
                .map(event -> new GetEventsDto(
                        event.getId(),
                        event.getEventType(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getDate(),
                        event.isCompleted()
                ))
                .collect(Collectors.toList());
    }

    public Event updateEvent(UpdateEventDto eventToUpdate) {

        var savedEvent = eventRepository.findById(eventToUpdate.id());

        savedEvent.setEventType(eventToUpdate.eventType());
        savedEvent.setTitle(eventToUpdate.title());
        savedEvent.setDescription(eventToUpdate.description());
        savedEvent.setDate(eventToUpdate.date());
        savedEvent.setCompleted(eventToUpdate.isCompleted());

        return eventRepository.save(savedEvent);
    }

    public void deleteEvent(Event event){
        eventRepository.delete(event);
    }

    public List<Event> getNearestEvents(Date startDate, Date endDate){
        List<Event> events = eventRepository.findByTimestampBetween(startDate, endDate);

        events.sort(Comparator.comparing(e -> e.getDate()));

        return events;
    }

}
