package ru.university.voronov.controller;

import ru.university.voronov.model.Birthday;
import ru.university.voronov.model.Event;
import ru.university.voronov.service.EventService;

import java.util.List;
import java.util.Scanner;

public class CmdController {

    private final EventService eventService;
    private final Scanner scanner;

    public CmdController(EventService eventService, Scanner scanner) {
        this.eventService = eventService;
        this.scanner = scanner;
    }

    public void run() {
        while (true) {
            System.out.println("\n=== Events menu ===");
            System.out.println("1. Показать все события");
            System.out.println("2. Найти событие по ID");
            System.out.println("3. Создать событие");
            System.out.println("4. Обновить событие");
            System.out.println("5. Удалить событие");
            System.out.println("0. Выход");
            System.out.println("Выберите действие: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        getAll();
                        break;

                    case "2":
                        getById();
                }
            }
        }
    }

    private void getAll() {
        List<Event> all = eventService.getAll();
        if (all.isEmpty()) {
            System.out.println("Нет созданных событий");
            return;
        }

        all.stream()
                .map(this::formatEvent)
                .forEach(System.out::println);
    }

    private String formatEvent(Event event) {
        String string = event.getClass() == Birthday.class ? String.format(", gift = $s", ((Birthday) event).getGift()) : "";
        return String.format("id = $s, title = $s, date = $s, name = $s, about = $s" + string, event.getId(), event.getTitle(), event.getDate(), event.getName(), event.getAbout());
    }
}
