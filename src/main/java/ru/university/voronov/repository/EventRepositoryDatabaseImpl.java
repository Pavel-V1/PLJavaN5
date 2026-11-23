package ru.university.voronov.repository;

import ru.university.voronov.model.Event;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class EventRepositoryDatabaseImpl implements EventRepository {

    private final DataSource dataSource;

    public EventRepositoryDatabaseImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Event> getAll() {
        var query = "SELECT * FROM event";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            var events = new ArrayList<Event>();
            while (resultSet.next()) {
                var event = new Event(
                        resultSet.getObject("id", Date.class),
                        resultSet.getString("title")
                );
                events.add(event);
            }

            return events;
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public Event create(Event event) {
        return null;
    }

    @Override
    public Event update(Event event) {
        return null;
    }

    @Override
    public boolean delete(Date date) {
        return false;
    }

    @Override
    public Optional<Event> getByDate(Date date) {
        return Optional.empty();
    }
}
