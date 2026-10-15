package ru.university.voronov.controller;

import ru.university.voronov.model.Birthday;
import ru.university.voronov.model.Event;
import ru.university.voronov.model.Gift;
import ru.university.voronov.model.Meeting;
import ru.university.voronov.service.EventService;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

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

    private void getById() {
        System.out.println("Введите ID события: ");
        String rawId = scanner.nextLine();
        try {
            UUID id = UUID.fromString(rawId);
            eventService.getById(id)
                    .map(this::formatEvent)
                    .ifPresentOrElse(
                            System.out::println,
                            () -> System.out.println("Задачи с таким идентификатором не найдено")
                    );
        } catch (Exception e) {
            System.out.println("Неверно задан идентификатор");
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
        System.out.println("Введите дату события: ");
        Date date = Date.from(Instant.parse(scanner.nextLine()));
        System.out.println("Введите время начала события: ");
        int beginning = scanner.nextInt();
        System.out.println("Введите описание: ");
        String about = scanner.nextLine();
        Event event;
        if (typeInt == 1) {
            System.out.println("Введите подарок: ");
//            String rawGift = scanner.nextLine();
//            try {
//                UUID giftID = UUID.fromString(rawGift);
//                Gift gift = giftService.getById(giftID);
//            } catch (Exception e) {
//                Gift gift = new Gift(rawGift, UUID.randomUUID());
//            }
            Gift gift = new Gift(scanner.nextLine(), UUID.randomUUID());
            // Да, да, по-хорошему, раз уж я по такому пути пошел, то должен и отдельные репозиторий с сервисом сделать, а это костыль.
            // Но я отложу эту задачу на потом, ведь сейчас главное - события, а подарки можно и на строки заменить.
            event = new Birthday(UUID.randomUUID(), title, date, name, beginning, gift, about);
        } else {
            event = new Meeting(UUID.randomUUID(), title, date, name, beginning, about);
        }
        Event created = eventService.create(event);
        System.out.println("Создано событие: " + formatEvent(created));
    }

    private void updateEvent() {
        eventService.getById(getUuid())
                .ifPresentOrElse(
                        this::updateThisEvent,
                        () -> System.out.println("Нет задачи с таким идентификатором")
                );
    }

    private void deleteEvent() {
        eventService.deleteById(getUuid());
    }

    private String formatEvent(Event event) {
        String string;
        if (event.getClass() == Birthday.class) {
            string = String.format(", gift = $s", ((Birthday) event).getGift().getContent());
        } else {
            string = "";
        }
        return String.format("id = $s, title = $s, date = $s, name = $s, about = $s" + string,
                event.getId(), event.getTitle(), event.getDate(), event.getName(), event.getAbout());
    }

    private UUID getUuid() {
        UUID uuid;
        while (true) {
            System.out.println("Введите идентификатор события: ");
            String id = scanner.nextLine();
            try {
                uuid = UUID.fromString(id);
                break;
            } catch (Exception e) {
                System.out.println("Неверно задан идентификатор. Введите заново");
            }
        }
        return uuid;
    }

    private void updateThisEvent(Event event) {
        String add = event.getClass() == Birthday.class ? ";\n6. Подарок" : "";
        System.out.println("1. Заголовок;\n2. Дата;\n3. Имя;\n4. Время начала;\n5. Описание" + add + ".\nВведите, какие данные хотите обновить: ");
        String choices = scanner.nextLine();
        boolean was = false;
        if (choices.contains("1")) {
            System.out.println("Введите новый заголовок события: ");
            String newTitle = scanner.nextLine();
            event.setTitle(newTitle);
            was = true;
        }
        if (choices.contains("2")) {
            System.out.println("Введите новую дату: ");
            Date newDate = Date.from(Instant.parse(scanner.nextLine()));
            event.setDate(newDate);
            was = true;
        }
        if (choices.contains("3")) {
            System.out.println("Введите новое имя: ");
            String newName = scanner.nextLine();
            event.setName(newName);
            was = true;
        }
        if (choices.contains("4")) {
            System.out.println("Введите новое время начала: ");
            int newBeginning = scanner.nextInt();
            event.setBeginning(newBeginning);
            was = true;
        }
        if (choices.contains("5")) {
            System.out.println("Введите новое описание: ");
            String newAbout = scanner.nextLine();
            event.setAbout(newAbout);
            was = true;
        }
        if (choices.contains("6") && event.getClass() == Birthday.class) {
            System.out.println("Введите новый подарок: ");
            Gift gift = new Gift(scanner.nextLine(), UUID.randomUUID());
            ((Birthday) event).setGift(gift);
            was = true;
        }

        if (was) {
            eventService.update(event);
        } else {
            System.out.println("Изменения не были обнаружены");
        }
    }
}
