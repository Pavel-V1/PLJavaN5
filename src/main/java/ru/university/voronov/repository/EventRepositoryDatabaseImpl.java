package ru.university.voronov.repository;

import ru.university.voronov.model.Birthday;
import ru.university.voronov.model.Event;
import ru.university.voronov.model.Meeting;

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
                Date date = resultSet.getObject("date", Date.class);
                String title = resultSet.getString("title");
                String name = resultSet.getString("name");
                String beginning = resultSet.getString("beginning");
                String about = resultSet.getString("about");
                Event event;
                if (resultSet.getBoolean("isBirthday")) {
                    event = new Meeting(title, date, name, beginning, about);
                } else {
                    event = new Birthday(
                            title, date, name, beginning, about,
                            resultSet.getString("gift")
                    );
                }
                events.add(event);
            }

            return events;
        } catch (Exception e) {
            return List.of();
        }
    }

    @Override
    public Event create(Event event) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            Date date = event.getDate();
            String title = event.getTitle();
            String name = event.getName();
            String beginning = event.getBeginning();
            String about = event.getAbout();
            boolean isBirthday = event instanceof Birthday;
            String gift;
            if (isBirthday) {
                gift = ((Birthday) event).getGift();
            } else {
                gift = "-";
            }
            var request = String.format("INSERT INTO event (title, date, name, beginning, about, gift, isBirthday) " +
                    "VALUES (%s, %s, %s, %s, %s, %s, %b)", title, date, name, beginning, about, gift, isBirthday);
            statement.executeUpdate(request);

            return event;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Event update(Event event) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            var request = String.format("UPDATE event SET title = %s, date = %s, name = %s, " +
                            "beginning = %s, about = %s, gift = %s WHERE date = %s",
                    event.getTitle(),

            );
        } catch (Exception e) {
            return null;
        }
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
