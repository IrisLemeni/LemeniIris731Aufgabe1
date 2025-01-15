package org.example;

import java.util.Date;

public class Ereignis {
    private int id;
    private String name;
    public enum Haus{
        Stark, Lannister, Targaryen, Baratheon, Greyjoy, Martell, Tyrell
    }
    private Haus haus;
    private String description;
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public Haus getHaus() {
        return haus;
    }

    public void setHaus(Haus haus) {
        this.haus = haus;
    }
}
