package ru.university.voronov.model;

import java.util.Date;

public class Birthday extends Event {
    private String gift;  // Можно было и строкой.

    public Birthday(String title, Date date, String name, String beginning, String gift, String about) {
        this.setDate(date);
        this.setName(name);
        this.setBeginning(beginning);
        this.gift = gift;  // Мы же не пойдем без подарка.
        this.setAbout(about);
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }
}
