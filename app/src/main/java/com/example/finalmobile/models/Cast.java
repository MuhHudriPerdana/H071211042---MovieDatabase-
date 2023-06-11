package com.example.finalmobile.models;

public class Cast {
    int id;
    String character;
    String name;
    String profile_path;


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

    public String getProfile_path() {
        return profile_path;
    }

    public String getCharacter() {
        return "("+character+")";
    }
}
