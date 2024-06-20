package it.unimib.sd2024.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Order {
    private String id;
    private String userEmail;
    private String domain;
    private Boolean registration; //true: Registration, false: Renewal
    private double paid;
    private LocalDate orderDate;


    public Order() {
    }

    public Order(String id, String userEmail, String domain, Boolean registration, double paid, LocalDate orderDate) {
        this.id = id;
        this.userEmail = userEmail;
        this.domain = domain;
        this.registration = registration;
        this.paid = paid;
        this.orderDate = orderDate;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Boolean isRegistration() {
        return this.registration;
    }

    public Boolean getRegistration() {
        return this.registration;
    }

    public void setRegistration(Boolean registration) {
        this.registration = registration;
    }

    public double getPaid() {
        return this.paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public LocalDate getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", userEmail='" + getUserEmail() + "'" +
            ", domain='" + getDomain() + "'" +
            ", registration='" + isRegistration() + "'" +
            ", paid='" + getPaid() + "'" +
            ", orderDate='" + getOrderDate() + "'" +
            "}";
    }

    
}