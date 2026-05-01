package com.cartracker.model;

/**
 * Vehicle class represents a vehicle in the system.
 * Encapsulation: All fields are private.
 */
public class Vehicle {
    private String id;
    private String name;
    private String number;
    private String make;
    private String model;
    private int year;
    private String type; // GASOLINE or ELECTRIC

    public Vehicle() {
    }

    public Vehicle(String id, String name, String number, String make, String model, int year, String type) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.make = make;
        this.model = model;
        this.year = year;
        this.type = type;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return id + "|" + name + "|" + number + "|" + make + "|" + model + "|" + year + "|" + type;
    }
}
