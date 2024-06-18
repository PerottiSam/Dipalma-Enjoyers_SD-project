package it.unimib.sd2024;

import java.time.LocalDate;

public class Domain{
    private String domainName;
    private LocalDate registrationDate;
    private LocalDate expirationDate;

    public Domain(String domainName, LocalDate registrationDate, LocalDate expirationDate) {
        this.domainName = domainName;
        this.registrationDate = registrationDate;
        this.expirationDate = expirationDate;
    }

    public String getDomainName() {
        return this.domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
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
            ", registrationDate='" + getRegistrationDate() + "'" +
            ", expirationDate='" + getExpirationDate() + "'" +
            "}";
    }
    
}