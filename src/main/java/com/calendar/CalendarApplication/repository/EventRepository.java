package com.calendar.CalendarApplication.repository;

import com.calendar.CalendarApplication.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    public List<Event> findByUserId(long userId);
    public Event findById(long id);
}
