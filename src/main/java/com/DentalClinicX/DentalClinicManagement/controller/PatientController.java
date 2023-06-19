package com.DentalClinicX.DentalClinicManagement.controller;

import com.DentalClinicX.DentalClinicManagement.model.dto.AppointmentDTO;
import com.DentalClinicX.DentalClinicManagement.model.dto.PatientDTO;
import com.DentalClinicX.DentalClinicManagement.model.wrapper.AppointmentWrapper;
import com.DentalClinicX.DentalClinicManagement.persistance.repositoryMongo.IAppointmentRepositoryMongo;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Address;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Appointment;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Patient;
import com.DentalClinicX.DentalClinicManagement.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientController {
    private final PatientService patientService;


    @Autowired
    public PatientController(PatientService patientService, IAppointmentRepositoryMongo mongoAppointmentRepository) {
        this.patientService = patientService;

    }

    @PostMapping("/patients/add")
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
        Address address = patient.getAddress();
        Patient serviceResponse = patientService.addPatient(patient, address);
        if (serviceResponse == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(serviceResponse, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/patients/delete")
    public ResponseEntity<String> deletePatient(@RequestParam Long id) {
        Patient serviceResponse = patientService.deletePatient(id);
        if (serviceResponse == null) {
            return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>("The patient has been deleted", HttpStatus.OK);
        }
    }

    @GetMapping("/patients/search")
    public <T> ResponseEntity<List<Patient>> findPatients(@RequestParam T info) {
        List<Patient> patientList = patientService.findPatient(info);
        if (patientList.isEmpty()) {
            return new ResponseEntity<>(patientList, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(patientList, HttpStatus.OK);
        }
    }

    @PutMapping("patients/update")
    public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient) {
        Patient serviceResponse = patientService.modifyPatient(patient);
        if (serviceResponse == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
        }
    }

    @GetMapping("patients/listAll")
    public ResponseEntity<List<PatientDTO>> listPatients() {
        List<PatientDTO> serviceResponse = patientService.listPatients();
        if (serviceResponse == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
        }
    }

    @PostMapping("patients/addAppointment")
    public ResponseEntity<Appointment> addAppointment(@RequestBody AppointmentWrapper appointment) {
        Appointment serviceResponse = patientService.addAppointment(appointment);
        if(serviceResponse == null){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(serviceResponse,HttpStatus.CREATED);
        }
    }

    @GetMapping("patients/listAppointments")
    public ResponseEntity<List<AppointmentDTO>> listAppointments(@RequestParam Long id) {
        List<AppointmentDTO> serviceResponse = patientService.listAppointments(id);
        if (serviceResponse == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
        }
    }
}
