package ru.university.voronov.model;

import java.util.UUID;

public class Gift {
    private String content;
    private UUID id;

    public Gift(String content, UUID id) {
        this.content = content;
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
