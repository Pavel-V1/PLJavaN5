package ru.university.voronov.exception;

import java.util.UUID;

public class EventNotFoundException extends BusinessException {
    public EventNotFoundException(UUID id) {
        super("Event not found with id = " + id);
    }
}
