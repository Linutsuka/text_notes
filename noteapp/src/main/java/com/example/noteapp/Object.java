package com.example.noteapp;

public class Object {

    public String description;
    public String percent;

    public Object(String description,String percent) {
        this.description = description;
        this.percent = percent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
