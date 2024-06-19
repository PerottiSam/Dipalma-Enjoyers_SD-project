package it.unimib.sd2024.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Domain{
    private String domainName;
    private String userEmail;
    private LocalDate registrationDate;
    private LocalDate expirationDate;


    public Domain() {
    }

    public Domain(String domainName, String userEmail, LocalDate registrationDate, LocalDate expirationDate) {
        this.domainName = domainName;
        this.userEmail = userEmail;
        this.registrationDate = registrationDate;
        this.expirationDate = expirationDate;
    }

    public String getDomainName() {
        return this.domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setuserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public LocalDate getRegistrationDate() {
        return this.registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDate getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

   
    @Override
    public String toString() {
        return "{" +
            " domainName='" + getDomainName() + "'" +
            ", userEmail='" + getUserEmail() + "'" +
            ", registrationDate='" + getRegistrationDate() + "'" +
            ", expirationDate='" + getExpirationDate() + "'" +
            "}";
    }
    
}