package ru.university.voronov.exception;

public class EventTitleException extends BusinessException {
    public EventTitleException() {
        super("Title is blank");
    }
}
