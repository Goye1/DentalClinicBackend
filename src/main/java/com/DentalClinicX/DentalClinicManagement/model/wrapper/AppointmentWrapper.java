package com.DentalClinicX.DentalClinicManagement.model.wrapper;

import java.time.LocalDateTime;

public class AppointmentWrapper {
    private LocalDateTime appointmentDate;
    private Long patient_id;
    private Long dentist_id;
    private String reason;

    public AppointmentWrapper() {
    }

    public AppointmentWrapper(LocalDateTime appointmentDate, long patient_id, long dentist_id, String reason) {
        this.appointmentDate = appointmentDate;
        this.patient_id = patient_id;
        this.dentist_id = dentist_id;
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Long getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(Long patient_id) {
        this.patient_id = patient_id;
    }

    public Long getDentist_id() {
        return dentist_id;
    }

    public void setDentist_id(Long dentist_id) {
        this.dentist_id = dentist_id;
    }


}
