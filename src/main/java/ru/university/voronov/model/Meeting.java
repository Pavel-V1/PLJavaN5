package ru.university.voronov.model;

import java.util.Date;

public class Meeting extends Event {

    public Meeting(String title, Date date, String name, String beginning, String about) {
        this.setDate(date);
        this.setName(name);
        this.setBeginning(beginning);
        this.setAbout(about);
    }
}
