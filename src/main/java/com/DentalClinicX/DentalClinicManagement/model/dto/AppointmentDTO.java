package com.DentalClinicX.DentalClinicManagement.model.dto;

import com.DentalClinicX.DentalClinicManagement.persistance.entity.Dentist;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Patient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.time.LocalDate;
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentDTO implements Serializable {
    private PatientDTO patient;
    private DentistDTO dentist;
    private LocalDate appointmentDate;

    public AppointmentDTO() {
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }

    public DentistDTO getDentist() {
        return dentist;
    }

    public void setDentist(DentistDTO dentist) {
        this.dentist = dentist;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}
