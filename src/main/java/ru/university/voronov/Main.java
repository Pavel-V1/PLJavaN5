package ru.university.voronov;


import org.postgresql.ds.PGSimpleDataSource;
import ru.university.voronov.controller.CmdController;
import ru.university.voronov.model.Birthday;
import ru.university.voronov.model.Event;
import ru.university.voronov.model.Meeting;
import ru.university.voronov.repository.EventRepository;
import ru.university.voronov.repository.EventRepositoryDatabaseImpl;
import ru.university.voronov.service.EventService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

/*
  1. Написать консольное приложение
  2. CRUD, create, read, update, delete
  3. Хранение данных в Map или List
  4. todo list для демонстрации
 */

public class Main {
    public static void main(String[] args) {
        DataSource ds = createDataSource();
        EventRepository eventRepository = new EventRepositoryDatabaseImpl(ds);
        EventService eventService = new EventService(eventRepository);
        Scanner scanner = new Scanner(System.in);
        CmdController cmdController = new CmdController(eventService, scanner);
        cmdController.run();
    }

    public static DataSource createDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setServerNames(new String[]{"localhost"});
        dataSource.setPortNumbers(new int[]{5434});
        dataSource.setDatabaseName("event");
        dataSource.setUser("event");
        dataSource.setPassword("event");

        return dataSource;
    }

//    public static void initializeDatabase(DataSource dataSource) {
//        try (Connection connection = dataSource.getConnection();
//             Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery(query)) {
//
//            statement.executeUpdate();
//
//            var events = new ArrayList<Event>();
//            while (resultSet.next()) {
//                Date date = resultSet.getObject("date", Date.class);
//                String title = resultSet.getString("title");
//                String name = resultSet.getString("name");
//                String beginning = resultSet.getString("beginning");
//                String about = resultSet.getString("about");
//                Event event;
//                if (resultSet.getBoolean("isBirthday")) {
//                    event = new Meeting(title, date, name, beginning, about);
//                } else {
//                    event = new Birthday(
//                            title, date, name, beginning, about,
//                            resultSet.getString("gift")
//                    );
//                }
//                events.add(event);
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
