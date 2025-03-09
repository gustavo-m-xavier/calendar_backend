package com.calendar.CalendarApplication.utils;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

@Component
public class DotenvUtil {

    private final Dotenv dotenv;

    public DotenvUtil() {
        this.dotenv = Dotenv.load();
    }

    public String getValue(String key) {
        return dotenv.get(key);
    }
}
