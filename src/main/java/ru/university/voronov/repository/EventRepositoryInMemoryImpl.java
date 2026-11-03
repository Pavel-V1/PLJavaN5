package ru.university.voronov.repository;

import ru.university.voronov.model.Event;

import java.util.*;

public class EventRepositoryInMemoryImpl implements EventRepository {
    private final Map<Date, Event> data;

    public EventRepositoryInMemoryImpl() {
        this.data = new HashMap<>();
    }

    public EventRepositoryInMemoryImpl(Map<Date, Event> data) {
        this.data = data;
    }

    public Event create(Event newEvent) {
        data.put(newEvent.getDate(), newEvent);
        return newEvent;
    }

    public Event update(Event updatedEvent) {
        data.put(updatedEvent.getDate(), updatedEvent);
        return updatedEvent;
    }

    public boolean delete(Date date) {
        return data.remove(date) != null;
    }

    public Optional<Event> getByDate(Date date) {
        return Optional.ofNullable(data.get(date));
    }

    public List<Event> getAll() {
        return data.values().stream().toList();
    }
}
