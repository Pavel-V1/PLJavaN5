package ru.university.voronov.exception;

import java.util.UUID;

public class EventNotFoundException extends BusinessException {
    public EventNotFoundException(UUID uuid) {
        super("Event not found with id = " + uuid);
    }
}
