package com.vineetsridhar.bored;

public class Event {
    private String name, title, location, desc, number, id, key;

    public Event(String name, String title, String location, String desc, String number, String id, String key) {
        this.name = name;
        this.title = title;
        this.location = location;
        this.desc = desc;
        this.number = number;
        this.id = id;
        this.key = key;
    }
    public String getKey() { return key; }

    public String getId() { return id; }
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
