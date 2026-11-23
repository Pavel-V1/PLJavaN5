package ru.university.voronov.repository;

import ru.university.voronov.model.Birthday;
import ru.university.voronov.model.Event;
import ru.university.voronov.model.Meeting;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.*;

public class EventRepositoryDatabaseImpl implements EventRepository {

    private final DataSource dataSource;
    private final String GIFT_DEFAULT = "-";

    public EventRepositoryDatabaseImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Event> getAll() {
        var query = "SELECT * FROM event;";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            var events = new ArrayList<Event>();
            while (resultSet.next()) {
                events.add(getEvent(resultSet));
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

            var request = String.format(
                    "INSERT INTO event (title, date, name, beginning, about, gift, isBirthday) " +
                    "VALUES (%s, %s, %s, %s, %s, %s, %b);",
                    event.getTitle(),
                    event.getDate(),
                    event.getName(),
                    event.getBeginning(),
                    event.getAbout(),
                    checkForGift(event),
                    event instanceof Birthday
            );
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

            var request = String.format(
                    "UPDATE event SET " +
                    "title = %s, date = %s, name = %s, beginning = %s, about = %s, gift = %s " +
                    "WHERE id = %s;",
                    event.getTitle(),
                    event.getDate(),
                    event.getName(),
                    event.getBeginning(),
                    event.getAbout(),
                    checkForGift(event),
                    event.getId()
            );
            statement.executeUpdate(request);
            return event;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean delete(UUID id) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            var request = String.format("DELETE FROM event WHERE id = %s;", id);
            statement.execute(request);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Optional<Event> getById(UUID id) {
        var request = String.format("SELECT * FROM event WHERE id = %s;", id);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(request)) {

            return Optional.ofNullable(getEvent(resultSet));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Event>> getByDate(Date date) {
        String[] str = date.toString().split(" ");
        str[3] = "00:00:00";
        Date date1 = Date.from(Instant.parse(Arrays.toString(str)));
        str[3] = "23:59:59";
        Date date2 = Date.from(Instant.parse(Arrays.toString(str)));

        var query = String.format("SELECT * FROM event WHERE %s BETWEEN %s AND %s;", date, date1, date2);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            var events = new ArrayList<Event>();
            while (resultSet.next()) {
                events.add(getEvent(resultSet));
            }

            return Optional.of(events);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private String checkForGift(Event event) {
        String gift;
        if (event instanceof Birthday) {
            gift = ((Birthday) event).getGift();
        } else {
            gift = GIFT_DEFAULT;
        }
        return gift;
    }

    private Event getEvent(ResultSet resultSet) throws SQLException {
        UUID id = resultSet.getObject("id", UUID.class);
        Date date = resultSet.getObject("date", Date.class);
        String title = resultSet.getString("title");
        String name = resultSet.getString("name");
        String beginning = resultSet.getString("beginning");
        String about = resultSet.getString("about");
        Event event;
        if (resultSet.getBoolean("isBirthday")) {
            event = new Meeting(id, title, date, name, beginning, about);
        } else {
            event = new Birthday(
                    id, title, date, name, beginning, about,
                    resultSet.getString("gift")
            );
        }
        return event;
    }
}
