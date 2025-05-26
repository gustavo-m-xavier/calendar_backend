package com.calendar.CalendarApplication.repository;

import com.calendar.CalendarApplication.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByUserId(long userId);
    Event findById(long id);
    List<Event> findByTimestampBetween(Date startTime, Date endTime);
}
