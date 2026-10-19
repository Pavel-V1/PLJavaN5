package ru.university.voronov.exception;

public class EventNameException extends BusinessException {
    public EventNameException() {
        super("Name is blank");
    }
}
