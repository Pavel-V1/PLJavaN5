package ru.university.voronov.service;

import ru.university.voronov.exception.*;
import ru.university.voronov.model.Birthday;
import ru.university.voronov.model.Event;
import ru.university.voronov.repository.EventRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    private void check(Event event) {
        if (event.getTitle().isBlank()) {
            throw new EventTitleException();
        } else if (event.getName().isBlank()) {
            throw new EventNameException();
        } else if (event.getDate() == null) {
            throw new EventDateException();
        } else if (event.getClass() == Birthday.class && ((Birthday) event).getGift() == null) {
            throw new BirthdayGiftException();
        }
    }

    public Event create(Event newEvent) {
        check(newEvent);

        return eventRepository.create(newEvent);
    }

    public Event update(Event updatedEvent) {
        check(updatedEvent);

        return eventRepository.getById(updatedEvent.getId())
                .map(_ -> eventRepository.update(updatedEvent))
                .orElseThrow(() -> new EventNotFoundException(updatedEvent.getId()));
    }

    public boolean deleteById(UUID id) {
        return eventRepository.getById(id)
                .map(_ -> eventRepository.delete(id))
                .orElseThrow(() -> new EventNotFoundException(id));
    }

    public Optional<Event> getById(UUID id) {
        return eventRepository.getById(id);
    }

    public List<Event> getAll() {
        return eventRepository.getAll();
    }
}
