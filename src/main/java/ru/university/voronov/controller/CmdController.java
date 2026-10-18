package ru.university.voronov.controller;

import ru.university.voronov.model.Birthday;
import ru.university.voronov.model.Event;
import ru.university.voronov.model.Meeting;
import ru.university.voronov.service.EventService;

import java.time.Instant;
import java.util.Date;
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
            System.out.println("2. Найти событие по дате");
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
                        getByDate();
                        break;

                    case "3":
                        createEvent();
                        break;

                    case "4":
                        updateEvent();
                        break;

                    case "5":
                        deleteEvent();
                        break;

                    case "0":
                        System.out.println("Выход из программы, пока!");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Неверный ввод. Попробуйте еще раз");
                }
            } catch (Exception e) {
                System.out.println("Возникла непредвиденная ошибка: " + e.getMessage());
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

    private void getByDate() {
        System.out.println("Введите день/месяц/год события: ");
        String rawDate = scanner.nextLine();
        try {
            Date date = Date.from(Instant.parse(rawDate));
            eventService.getByDate(date)
                    .map(this::formatEvent)
                    .ifPresentOrElse(
                            System.out::println,
                            () -> System.out.println("Задачи с такой датой не найдено")
                    );
        } catch (Exception e) {
            System.out.println("Неверно задана дата");
        }
    }

    private void createEvent() {
        int typeInt;
        do {
            System.out.println("Типы событий:\n1. День рождения;\n2. Встреча.\nВыберите тип события: ");
            String type = scanner.nextLine();
            try {
                switch (type) {
                    case "1":
                        typeInt = 1;
                        break;

                    case "2":
                        typeInt = 2;
                        break;

                    default:
                        System.out.println("Неверный ввод. Введите число, обозначающее нужный тип события");
                        continue;
                }
            } catch (Exception e) {
                System.out.println("Возникла непредвиденная ошибка: " + e.getMessage());
                continue;
            }
            break;
        } while (true);
        System.out.println("Введите заголовок события: ");
        String title = scanner.nextLine();
        System.out.println("Введите имя человека, с которым связано событие: ");
        String name = scanner.nextLine();
        Date date = tryToGetDate();
        System.out.println("Введите время начала события: ");
        String beginning = scanner.nextLine();
        System.out.println("Введите описание: ");
        String about = scanner.nextLine();
        Event event;
        if (typeInt == 1) {
            System.out.println("Введите подарок: ");
            String gift = scanner.nextLine();
            event = new Birthday(title, date, name, beginning, gift, about);
        } else {
            event = new Meeting(title, date, name, beginning, about);
        }
        Event created = eventService.create(event);
        System.out.println("Создано событие: " + formatEvent(created));
    }

    private void updateEvent() {
        eventService.getByDate(tryToGetDate())
                .ifPresentOrElse(
                        this::updateThisEvent,
                        () -> System.out.println("Нет задачи с такой датой")
                );
    }

    private void deleteEvent() {
        eventService.deleteByDate(tryToGetDate());
    }

    private String formatEvent(Event event) {
        String string;
        if (event instanceof Birthday) {
            string = String.format(
                    ", gift = %s",
                    ((Birthday) event).getGift()
            );
        } else {
            string = "";
        }
        return String.format(
                "title = %s, date = %s, name = %s, about = %s%s",
                event.getTitle(),
                event.getDate(),
                event.getName(),
                event.getAbout(),
                string
        );
    }

    private Date tryToGetDate() {
        Date date;
        while (true) {
            System.out.println("Введите дату события: ");
            String rawDate = scanner.nextLine();
            try {
                date = Date.from(Instant.parse(rawDate));
                break;
            } catch (Exception e) {
                System.out.println("Неверно задана дата. Введите заново");
            }
        }
        return date;
    }

    private void updateThisEvent(Event event) {
        boolean isBirthday = event instanceof Birthday;
        String add = isBirthday ? ";\n6. Подарок" : "";
        System.out.println("1. Заголовок;\n2. Дата;\n3. Имя;\n4. Время начала;\n5. Описание" + add + ".\nВведите, какие данные хотите обновить: ");
        String choices = scanner.nextLine();
        boolean was = false;
        List<String> str = List.of("новый заголовок события", "новую дату", "новое имя", "новое время начала", "новое описание", "новый подарок");
        for (int i = 1; i <= 6; i++) {
            if (choices.contains(String.valueOf(i))) {
                if (!isBirthday && i == 6) break;
                System.out.println("Введите " + str.get(i-1) + ": ");
                String newArg = scanner.nextLine();
                if (i == 2) {
                    Date newDate;
                    try {
                        newDate = Date.from(Instant.parse(newArg));
                    } catch (Exception e) {
                        System.out.println("Неверно задана дата. Введите заново");
                        newDate = tryToGetDate();
                    }
                    event.setDate(newDate);
                } else {
                    switch (i) {
                        case 1 -> event.setTitle(newArg);
                        case 3 -> event.setName(newArg);
                        case 4 -> event.setBeginning(newArg);
                        case 5 -> event.setAbout(newArg);
                        case 6 -> ((Birthday) event).setGift(newArg);
                    }
                }
                was = true;
            }
        }

        if (was) {
            eventService.update(event);
        } else {
            System.out.println("Убедитесь, что ввод корректный");
        }
    }
}
