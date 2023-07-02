package com.DentalClinicX.DentalClinicManagement.persistance.entityMongo;

import com.DentalClinicX.DentalClinicManagement.persistance.entity.Appointment;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Set;

@Document
public class DischargedPatientMongo {
    @Id private String id;
    private String name;
    private String surname;
    private Integer idCard;
    private LocalDate dischargeDate;
    private Long patientID;
    public DischargedPatientMongo() {
    }

    public DischargedPatientMongo(String name, String surname, Integer idCard, LocalDate dischargeDate, Long patientID) {
        this.name = name;
        this.surname = surname;
        this.idCard = idCard;
        this.dischargeDate = dischargeDate;
        this.patientID = patientID;
    }

    public Integer getIdCard() {
        return idCard;
    }

    public void setIdCard(Integer idCard) {
        this.idCard = idCard;
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
