package ru.university.voronov.model;

import java.util.Date;

public abstract class Event {
    private Date date;
    private String name;
    private int beginning;
    private String about;
    private String title;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBeginning() {
        return beginning;
    }

    public void setBeginning(int beginning) {
        this.beginning = beginning;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
