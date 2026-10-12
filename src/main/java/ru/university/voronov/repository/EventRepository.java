package ru.university.voronov.repository;

import ru.university.voronov.model.Event;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository {

    List<Event> getAll();

    Event create(Event event);

    Event update(Event event);

    boolean delete(UUID id);

    Optional<Event> getById(UUID id);
}
