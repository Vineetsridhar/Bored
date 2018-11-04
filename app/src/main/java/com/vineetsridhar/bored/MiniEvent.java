package com.vineetsridhar.bored;

public class MiniEvent {
    private String id, title;

    public MiniEvent(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    @Override
    public String toString(){
        return title;
    }
}
