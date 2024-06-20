package it.unimib.sd2024.entities;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;


public class User{
    private String email; // E' univoca, usata come identificativo
    private String name;
    private String surname;
    private HashMap<Integer, Order> orders;

    public User() {
    }

    public User(String email, String name, String surname, HashMap<Integer,Order> orders) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.orders = orders;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public HashMap<Integer,Order> getOrders() {
        return this.orders;
    }

    public void setOrders(HashMap<Integer,Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "{" +
            " email='" + getEmail() + "'" +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", orders='" + getOrders() + "'" +
            "}";
    }    
}