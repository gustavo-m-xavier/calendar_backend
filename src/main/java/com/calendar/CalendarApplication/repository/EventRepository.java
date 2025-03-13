package com.calendar.CalendarApplication.repository;

import com.calendar.CalendarApplication.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
