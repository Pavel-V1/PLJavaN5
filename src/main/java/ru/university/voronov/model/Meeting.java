package ru.university.voronov.model;

import java.util.Date;
import java.util.UUID;

public class Meeting extends Event {

    public Meeting(UUID id, String title, Date date, String name, int beginning, String about) {
        this.setId(id);
        this.setDate(date);
        this.setName(name);
        this.setBeginning(beginning);
        this.setAbout(about);
    }
}
