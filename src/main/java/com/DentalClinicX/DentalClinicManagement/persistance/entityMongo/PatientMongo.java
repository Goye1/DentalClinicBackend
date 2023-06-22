package com.DentalClinicX.DentalClinicManagement.persistance.entityMongo;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
@Document
public class PatientMongo {
    @Id private String id;
    private String name;
    private String surname;
    private LocalDate dischargeDate;
    private Long patientID;

    public PatientMongo() {
    }

    public PatientMongo(String name, String surname, LocalDate dischargeDate, Long patientID) {
        this.name = name;
        this.surname = surname;
        this.dischargeDate = dischargeDate;
        this.patientID = patientID;
    }

    public Long getPatientID() {
        return patientID;
    }

    public void setPatientID(Long patientID) {
        this.patientID = patientID;
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

    public LocalDate getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(LocalDate dischargeDate) {
        this.dischargeDate = dischargeDate;
    }
}