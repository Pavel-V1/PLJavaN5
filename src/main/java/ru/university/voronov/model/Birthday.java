package ru.university.voronov.model;

import java.util.Date;

public class Birthday {
    private Date date;
    private String name;
    private int hourOfBirth;
    private Gift gift;  // Можно было и строкой.
    private String about;

    public Birthday(Date date, String name, int hourOfBirth, Gift gift, String about) {
        this.date = date;
        this.name = name;
        this.hourOfBirth = hourOfBirth;
        this.gift = gift;
        this.about = about;
    }

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

    public int getHourOfBirth() {
        return hourOfBirth;
    }

    public void setHourOfBirth(int hourOfBirth) {
        this.hourOfBirth = hourOfBirth;
    }

    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
