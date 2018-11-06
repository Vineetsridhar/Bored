package com.vineetsridhar.bored;

public class FirebaseAdapter {
    private String description, id, location, name, phone, title;
    private int time;

    public FirebaseAdapter(String description, String id, String location, String name, String phone, int time, String title) {
        this.description = description;
        this.id = id;
        this.location = location;
        this.name = name;
        this.phone = phone;
        this.title = title;
        this.time = time;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public FirebaseAdapter(){}

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getTitle() {
        return title;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return  "description='" + description + '\'' +
                ", id='" + id + '\'' +
                ", location='" + location + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", title='" + title + '\'' +
                ", time=" + time +
                '}';
    }
}
