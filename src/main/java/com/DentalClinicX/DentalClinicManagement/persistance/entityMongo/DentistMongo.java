package com.DentalClinicX.DentalClinicManagement.persistance.entityMongo;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document
public class DentistMongo {
    @Id private String id;
    private String name;
    private String surname;
    private int licenseNumber;

    public DentistMongo() {
    }

    public DentistMongo(String name, String surname, int licenseNumber) {
        this.name = name;
        this.surname = surname;
        this.licenseNumber = licenseNumber;
    }

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(int licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
}
