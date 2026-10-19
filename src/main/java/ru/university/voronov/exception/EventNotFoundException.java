package ru.university.voronov.exception;

import java.util.Date;

public class EventNotFoundException extends BusinessException {
    public EventNotFoundException(Date date) {
        super("Event not found with id = " + date);
    }
}
