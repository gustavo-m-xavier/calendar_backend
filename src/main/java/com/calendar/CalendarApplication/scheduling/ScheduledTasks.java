package com.calendar.CalendarApplication.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

@Component
public class ScheduledTasks {

    private static final Logger logger = Logger.getLogger(ScheduledTasks.class.getName());

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //schecule test
    @Scheduled(fixedRate = 5000)
    public void hourlyDueDateTask(){
        logger.info(dateFormat.format(new Date()));
    }
}
