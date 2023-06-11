package com.example.finalmobile.models;


import java.util.List;

public class CreditModel {
    private int id;
    private List<Cast> cast;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cast> getCast() {
        return cast;
    }
}