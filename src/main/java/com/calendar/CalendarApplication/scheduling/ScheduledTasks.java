package com.calendar.CalendarApplication.scheduling;

import com.calendar.CalendarApplication.entity.Event;
import com.calendar.CalendarApplication.services.EventService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Component
public class ScheduledTasks {

    private static final Logger logger = Logger.getLogger(ScheduledTasks.class.getName());

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private final EventService eventService;

    public ScheduledTasks(EventService eventService) {
        this.eventService = eventService;
    }

    //shoud be 86400000
    @Scheduled(fixedRate = 5000)
    public void dailyDueDateTask() {
        logger.info("Verificando eventos: " + dateFormat.format(new Date()));

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR, 24);
        Date nextDay = calendar.getTime();

        List<Event> upcomingEvents = eventService.getNearestEvents(now, nextDay);

        // must have logic to generate new notification and return it to websocket
        upcomingEvents.forEach(event ->
                logger.info("Evento próximo: " + event.getTitle())
        );
    }

    //shoud be 3600000
    @Scheduled(fixedRate = 5000)
    public void hourlyDueDateTask() {
        logger.info("Verificando eventos: " + dateFormat.format(new Date()));

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR, 24);
        Date nextDay = calendar.getTime();

        List<Event> upcomingEvents = eventService.getNearestEvents(now, nextDay);

        // must have logic to generate new notification and return it to websocket
        upcomingEvents.forEach(event ->
                logger.info("Evento próximo: " + event.getTitle())
        );
    }

}
