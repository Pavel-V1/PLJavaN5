package ru.university.voronov.repository;

import ru.university.voronov.model.Event;

import java.util.*;

public class EventRepositoryInMemoryImpl implements EventRepository {
    private final Map<UUID, Event> data;

    public EventRepositoryInMemoryImpl() {
        this.data = new HashMap<>();
    }

    public EventRepositoryInMemoryImpl(Map<UUID, Event> data) {
        this.data = data;
    }

    public Event create(Event newEvent) {
        data.put(newEvent.getId(), newEvent);
        return newEvent;
    }

    public Event update(Event updatedEvent) {
        data.put(updatedEvent.getId(), updatedEvent);
        return updatedEvent;
    }

    public boolean delete(UUID id) {
        return data.remove(id) != null;
    }

    public Optional<Event> getById(UUID id) {
        return Optional.ofNullable(data.get(id));
    }

    public List<Event> getAll() {
        return data.values().stream().toList();
    }
}
