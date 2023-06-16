package com.DentalClinicX.DentalClinicManagement.model.wrapper;

import java.time.LocalDate;

public class AppointmentWrapper {
    private LocalDate appointmentDate;
    private Long patient_id;
    private Long dentist_id;

    public AppointmentWrapper() {
    }

    public AppointmentWrapper(LocalDate appointmentDate, long patient_id, long dentist_id) {
        this.appointmentDate = appointmentDate;
        this.patient_id = patient_id;
        this.dentist_id = dentist_id;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
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
