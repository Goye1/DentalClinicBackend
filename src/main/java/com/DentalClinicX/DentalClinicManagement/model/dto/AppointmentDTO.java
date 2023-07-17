package com.DentalClinicX.DentalClinicManagement.model.dto;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Dentist;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDateTime;
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentDTO implements Serializable {
    private Long id;
    private PatientDTO patient;
    private Dentist dentist;
    private LocalDateTime appointmentDate;
    private String reason;

    public AppointmentDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }

    public Dentist getDentist() {
        return dentist;
    }

    public void setDentist(Dentist dentist) {
        this.dentist = dentist;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}
