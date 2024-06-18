package it.unimib.sd2024;

import java.time.LocalDate;
import java.util.HashMap;


public class User{
    private String email; // E' univoca, usata come identificativo
    private String name;
    private String surname;
    private LocalDate bday;
    private HashMap<Integer, Order> orders;


    public User(String email, String name, String surname, LocalDate bday) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.bday = bday;
    }

    public User(String email, String name, String surname) {
        this.email = email;
        this.name = name;
        this.surname = surname;
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

    public LocalDate getBday() {
        return this.bday;
    }

    public void setBday(LocalDate bday) {
        this.bday = bday;
    }

    public void addOrder(Order order){
        orders.put(order.getId(), order);
    }

    public void setOrders(HashMap<Integer,Order> orders) {
        this.orders = orders;
    }

    public HashMap<Integer,Order> getOrders() {
        return this.orders;
    }


    @Override
    public String toString() {
        return "{" +
            " email='" + getEmail() + "'" +
            ", name='" + getName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", bday='" + getBday() + "'" +
            ", orders='" + getOrders() + "'" +
            "}";
    }

}