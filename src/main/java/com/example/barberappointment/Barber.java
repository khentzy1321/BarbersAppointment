package com.example.barberappointment;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.time.LocalTime;

public class Barber {
    private final StringProperty name;
    private final StringProperty address;
    private final SimpleObjectProperty<LocalTime> time;
    private final SimpleObjectProperty<LocalDate> date;

    public Barber(String name, String address, LocalTime time, LocalDate date) {
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.time = new SimpleObjectProperty<>(time);
        this.date = new SimpleObjectProperty<>(date);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public StringProperty addressProperty() {
        return address;
    }

    public LocalTime getTime() {
        return time.get();
    }

    public void setTime(LocalTime time) {
        this.time.set(time);
    }

    public SimpleObjectProperty<LocalTime> timeProperty() {
        return time;
    }

    public LocalDate getDate() {
        return date.get();
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public SimpleObjectProperty<LocalDate> dateProperty() {
        return date;
    }
}
