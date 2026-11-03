package ru.university.voronov.exception;

public class BirthdayGiftException extends BusinessException {
    public BirthdayGiftException() {
        super("Gift is blank");
    }
}
