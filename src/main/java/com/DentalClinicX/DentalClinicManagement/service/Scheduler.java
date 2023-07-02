package com.DentalClinicX.DentalClinicManagement.service;

import com.DentalClinicX.DentalClinicManagement.exceptions.AlreadyExistsException;
import com.DentalClinicX.DentalClinicManagement.exceptions.ResourceNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
    private final PastAppointmentServiceMongo appointmentService;
    private final DischargedPatientServiceMongo dischargedPatientServiceMongo;

    public Scheduler(PastAppointmentServiceMongo appointmentService, DischargedPatientServiceMongo dischargedPatientServiceMongo) {
        this.appointmentService = appointmentService;
        this.dischargedPatientServiceMongo = dischargedPatientServiceMongo;
    }

    @Scheduled(cron = "0 * * * * ?", zone = "America/Argentina/Buenos_Aires")
    public void processExpiredAppointments() throws AlreadyExistsException, ResourceNotFoundException {
        appointmentService.processAppointments();
        dischargedPatientServiceMongo.processPatients();
    }
}
