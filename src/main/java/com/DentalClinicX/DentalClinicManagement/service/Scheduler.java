package com.DentalClinicX.DentalClinicManagement.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
    private final AppointmentServiceMongo appointmentService;
    private final PatientServiceMongo patientServiceMongo;

    public Scheduler(AppointmentServiceMongo appointmentService, PatientServiceMongo patientServiceMongo) {
        this.appointmentService = appointmentService;
        this.patientServiceMongo = patientServiceMongo;
    }

    @Scheduled(cron = "0 * * * * ?", zone = "America/Argentina/Buenos_Aires")
    public void processExpiredAppointments() {
        patientServiceMongo.processPatients();
        appointmentService.processAppointments();
    }
}
