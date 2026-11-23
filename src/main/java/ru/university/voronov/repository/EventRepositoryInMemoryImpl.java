package ru.university.voronov.repository;

import ru.university.voronov.model.Event;

import java.time.Instant;
import java.util.*;

public class EventRepositoryInMemoryImpl implements EventRepository {
    private Map<UUID, Event> data;
    private Map<Date, UUID> dates;

    public EventRepositoryInMemoryImpl() {
        this.data = new HashMap<>();
        this.dates = new HashMap<>();
    }

    public EventRepositoryInMemoryImpl(Map<UUID, Event> data) {
        this.data = data;
        data.forEach((uuid, event) -> this.dates.put(event.getDate(), uuid));
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

    public Optional<List<Event>> getByDate(Date date) {
        String[] str = date.toString().split(" ");
        str[3] = "00:00:00";
        Date date1 = Date.from(Instant.parse(Arrays.toString(str)));
        str[3] = "23:59:59";
        Date date2 = Date.from(Instant.parse(Arrays.toString(str)));

        List<UUID> ids = new ArrayList<>();
        dates.forEach((dateThis, uuid) -> {
            if (dateThis.compareTo(date1) >= 0 && dateThis.compareTo(date2) <= 0) {
                ids.add(uuid);
            }
        });
        List<Event> events = new ArrayList<>();
        ids.forEach(uuid -> events.add(data.get(uuid)));
        return Optional.of(events);
    }

    public Optional<Event> getById(UUID id) {
        return Optional.ofNullable(data.get(id));
    }

    public List<Event> getAll() {
        return data.values().stream().toList();
    }
}
