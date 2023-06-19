package com.DentalClinicX.DentalClinicManagement.persistance.entityMongo;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class AppointmentMongo {

    @Id private String id;

    private LocalDateTime localDateTime;
    private DentistMongo dentist;

    private PatientMongo patient;

    public AppointmentMongo() {
    }

    public AppointmentMongo(LocalDateTime localDateTime, DentistMongo dentist, PatientMongo patient) {
        this.localDateTime = localDateTime;
        this.dentist = dentist;
        this.patient = patient;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public DentistMongo getDentist() {
        return dentist;
    }

    public void setDentist(DentistMongo dentist) {
        this.dentist = dentist;
    }

    public PatientMongo getPatient() {
        return patient;
    }

    public void setPatient(PatientMongo patient) {
        this.patient = patient;
    }
}
