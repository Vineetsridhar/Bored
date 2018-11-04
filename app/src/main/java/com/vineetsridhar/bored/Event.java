package com.vineetsridhar.bored;

public class Event {
    private String name, title, location, desc, number;

    public Event(String name, String title, String location, String desc, String number) {
        this.name = name;
        this.title = title;
        this.location = location;
        this.desc = desc;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getDesc() {
        return desc;
    }

    public String getNumber() {
        return number;
    }
}
