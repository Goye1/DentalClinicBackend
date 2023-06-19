package com.DentalClinicX.DentalClinicManagement.service;

import com.DentalClinicX.DentalClinicManagement.service.AppointmentServiceMongo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AppointmentScheduler {
    private final AppointmentServiceMongo appointmentService;

    public AppointmentScheduler(AppointmentServiceMongo appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Scheduled(cron = "0 0 0 * * ?", zone = "America/Argentina/Buenos_Aires")
    public void processExpiredAppointments() {

        appointmentService.processAppointments();
    }
}
