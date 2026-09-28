package ru.university.voronov.model;

import java.util.Date;

public class Meeting {
    private Date date;
    private String interlocutor;
    private Date timeOfBeginning;
    private String about;

    public Meeting(Date date, String interlocutor, Date timeOfBeginning, String about) {
        this.date = date;
        this.interlocutor = interlocutor;
        this.timeOfBeginning = timeOfBeginning;
        this.about = about;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getInterlocutor() {
        return interlocutor;
    }

    public void setInterlocutor(String interlocutor) {
        this.interlocutor = interlocutor;
    }

    public Date getTimeOfBeginning() {
        return timeOfBeginning;
    }

    public void setTimeOfBeginning(Date timeOfBeginning) {
        this.timeOfBeginning = timeOfBeginning;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
