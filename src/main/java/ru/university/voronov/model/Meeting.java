package ru.university.voronov.model;

import java.util.Date;
import java.util.UUID;

public class Meeting extends Event {

    public Meeting(UUID id, String title, Date date, String name, String beginning, String about) {
        this.setId(id);
        this.setTitle(title);
        this.setDate(date);
        this.setName(name);
        this.setBeginning(beginning);
        this.setAbout(about);
    }
}
