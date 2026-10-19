package ru.university.voronov.service;

import ru.university.voronov.exception.*;
import ru.university.voronov.model.Birthday;
import ru.university.voronov.model.Event;
import ru.university.voronov.repository.EventRepository;

import java.util.List;
import java.util.Optional;
import java.util.Date;

public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event create(Event newEvent) {
        check(newEvent);

        return eventRepository.create(newEvent);
    }

    public Event update(Event updatedEvent) {
        check(updatedEvent);

        return eventRepository.getByDate(updatedEvent.getDate())
                .map(_ -> eventRepository.update(updatedEvent))
                .orElseThrow(() -> new EventNotFoundException(updatedEvent.getDate()));
    }

    public boolean deleteByDate(Date date) {
        return eventRepository.getByDate(date)
                .map(_ -> eventRepository.delete(date))
                .orElseThrow(() -> new EventNotFoundException(date));
    }

    public Optional<Event> getByDate(Date date) {
        return eventRepository.getByDate(date);
    }

    public List<Event> getAll() {
        return eventRepository.getAll();
    }

    private void check(Event event) {
        if (event.getTitle().isBlank()) {
            throw new EventTitleException();
        } else if (event.getName().isBlank()) {
            throw new EventNameException();
        } else if (event.getDate() == null) {
            throw new EventDateException();
        } else if (event.getClass() == Birthday.class && ((Birthday) event).getGift().isBlank()) {
            throw new BirthdayGiftException();
        }
    }
}
