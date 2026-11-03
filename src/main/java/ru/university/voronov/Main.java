package ru.university.voronov;

/*
  1. Написать консольное приложение
  2. CRUD, create, read, update, delete
  3. Хранение данных в Map или List
  4. todo list для демонстрации
 */

import ru.university.voronov.controller.CmdController;
import ru.university.voronov.model.Event;
import ru.university.voronov.repository.EventRepositoryInMemoryImpl;
import ru.university.voronov.service.EventService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Map<Date, Event> eventMap = new HashMap<>();
        EventRepositoryInMemoryImpl eventRepositoryInMemory = new EventRepositoryInMemoryImpl(eventMap);
        EventService eventService = new EventService(eventRepositoryInMemory);
        Scanner scanner = new Scanner(System.in);
        CmdController cmdController = new CmdController(eventService, scanner);
        cmdController.run();
    }
}
