package ru.university.voronov.model;

import java.util.Date;

public class Meeting extends Event {

    public Meeting(Date date, String name, int beginning, String about) {
        this.setDate(date);
        this.setName(name);
        this.setBeginning(beginning);
        this.setAbout(about);
    }
}
