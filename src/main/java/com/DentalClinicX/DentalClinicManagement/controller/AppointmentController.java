package com.DentalClinicX.DentalClinicManagement.controller;

import com.DentalClinicX.DentalClinicManagement.service.AppointmentServiceMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppointmentController {

    private final AppointmentServiceMongo appointmentServiceMongo;

    @Autowired
    public AppointmentController(AppointmentServiceMongo appointmentServiceMongo) {
        this.appointmentServiceMongo = appointmentServiceMongo;
    }





}
