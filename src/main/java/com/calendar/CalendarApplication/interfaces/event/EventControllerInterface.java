package com.calendar.CalendarApplication.interfaces.event;

import com.calendar.CalendarApplication.dtos.event.CreateEventDto;
import com.calendar.CalendarApplication.dtos.event.UpdateEventDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface EventControllerInterface {

    public ResponseEntity<?> createEvent(
            @RequestBody CreateEventDto createEventDto,
            @RequestHeader("Authorization") String token
    );

    public ResponseEntity<?> getEvent(
            @PathVariable long userId,
            @RequestHeader("Authorization") String token
    );

    public ResponseEntity<?> updateEvent(
            @RequestBody UpdateEventDto updateEventDto,
            @RequestHeader("Authorization") String token
    );

    public ResponseEntity<?> deleteEvent(
            @PathVariable long id,
            @RequestHeader("Authorization") String token
    );
}
