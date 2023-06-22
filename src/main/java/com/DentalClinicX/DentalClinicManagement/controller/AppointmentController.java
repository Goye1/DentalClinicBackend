package com.DentalClinicX.DentalClinicManagement.controller;

import com.DentalClinicX.DentalClinicManagement.persistance.entityMongo.AppointmentMongo;
import com.DentalClinicX.DentalClinicManagement.service.AppointmentServiceMongo;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AppointmentController {

    private final AppointmentServiceMongo appointmentServiceMongo;

    @Autowired
    public AppointmentController(AppointmentServiceMongo appointmentServiceMongo) {
        this.appointmentServiceMongo = appointmentServiceMongo;
    }

    @GetMapping("/pastAppointments")
    public ResponseEntity<List<AppointmentMongo>> listPastAppointments(@RequestParam Long id){
        List<AppointmentMongo> serviceResponse = appointmentServiceMongo.listAppointments(id);
        if(serviceResponse.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(serviceResponse,HttpStatus.OK);
        }
    }



}
