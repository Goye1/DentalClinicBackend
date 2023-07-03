package com.DentalClinicX.DentalClinicManagement.controllerTest;

import com.DentalClinicX.DentalClinicManagement.exceptions.ResourceNotFoundException;
import com.DentalClinicX.DentalClinicManagement.persistance.entityMongo.PastAppointmentMongo;
import com.DentalClinicX.DentalClinicManagement.service.PastAppointmentServiceMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AppointmentController {

    private final PastAppointmentServiceMongo pastAppointmentServiceMongo;

    @Autowired
    public AppointmentController(PastAppointmentServiceMongo pastAppointmentServiceMongo) {
        this.pastAppointmentServiceMongo = pastAppointmentServiceMongo;
    }

    @GetMapping("patients/pastAppointments")
    public ResponseEntity<List<PastAppointmentMongo>> listPatientPastAppointments(@RequestParam Long idCard) throws ResourceNotFoundException {
        List<PastAppointmentMongo> serviceResponse = pastAppointmentServiceMongo.listPatientPastAppointments(idCard);
        return new ResponseEntity<>(serviceResponse,HttpStatus.OK);
    }

    @GetMapping("dentists/pastAppointments")
    public ResponseEntity<List<PastAppointmentMongo>> listDentistPastAppointments(@RequestParam Integer licenseNumber) throws ResourceNotFoundException {
        List<PastAppointmentMongo> serviceResponse = pastAppointmentServiceMongo.listDentistPastAppointments(licenseNumber);
        return new ResponseEntity<>(serviceResponse,HttpStatus.OK);
    }



}
