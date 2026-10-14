package ru.university.voronov.model;

import java.util.Date;
import java.util.UUID;

public class Birthday extends Event {
    private Gift gift;  // Можно было и строкой.

    public Birthday(UUID id, String title, Date date, String name, int beginning, Gift gift, String about) {
        this.setId(id);
        this.setDate(date);
        this.setName(name);
        this.setBeginning(beginning);
        this.gift = gift;  // Мы же не пойдем без подарка.
        this.setAbout(about);
    }

    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }
}
