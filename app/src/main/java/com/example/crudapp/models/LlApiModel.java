package com.example.crudapp.models;

public class LlApiModel {
    private int id;
    private String name, fName, gender;

    public LlApiModel(int id, String name, String fName, String gender) {
        this.id = id;
        this.name = name;
        this.fName = fName;
        this.gender = gender;
    }

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

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
