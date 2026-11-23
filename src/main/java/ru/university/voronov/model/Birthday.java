package ru.university.voronov.model;

import java.util.Date;
import java.util.UUID;

public class Birthday extends Event {
    private String gift;

    public Birthday(UUID id, String title, Date date, String name, String beginning, String about, String gift) {
        this.setId(id);
        this.setTitle(title);
        this.setDate(date);
        this.setName(name);
        this.setBeginning(beginning);
        this.setAbout(about);
        this.gift = gift;  // Мы же не пойдем без подарка.
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }
}
