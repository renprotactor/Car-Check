package com.carcheck.pages;

public class Car {
    String regNo;
    String make;
    String model;
    String color;
    String year;

    public Car(String regNo, String make, String model, String color, String year) {
        this.regNo = regNo;
        this.make = make;
        this.model = model;
        this.color = color;
        this.year = year;
    }

    public String getRegNo() {
        return regNo;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public String getYear() {
        return year;
    }
}
