package ru.university.voronov.exception;

public class EventDateException extends BusinessException {
    public EventDateException() {
        super("Date is blank");
    }
}
