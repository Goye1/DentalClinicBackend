package com.DentalClinicX.DentalClinicManagement.controller;
import com.DentalClinicX.DentalClinicManagement.exceptions.AlreadyExistsException;
import com.DentalClinicX.DentalClinicManagement.exceptions.ResourceNotFoundException;
import com.DentalClinicX.DentalClinicManagement.model.dto.AppointmentDTO;
import com.DentalClinicX.DentalClinicManagement.model.dto.PatientDTO;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Dentist;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Patient;
import com.DentalClinicX.DentalClinicManagement.persistance.entityMongo.PastAppointmentMongo;
import com.DentalClinicX.DentalClinicManagement.service.DentistService;
import com.DentalClinicX.DentalClinicManagement.service.PastAppointmentServiceMongo;
import com.DentalClinicX.DentalClinicManagement.service.PatientService;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/admins")
public class AdminController {

    private final DentistService dentistService;
    private final PatientService patientService;

    private final PastAppointmentServiceMongo pastAppointmentServiceMongo;

    @Autowired
    public AdminController(DentistService dentistService, PatientService patientService, PastAppointmentServiceMongo pastAppointmentServiceMongo) {
        this.dentistService = dentistService;
        this.patientService = patientService;
        this.pastAppointmentServiceMongo = pastAppointmentServiceMongo;
    }

    @PostMapping("/add")
    public ResponseEntity<Dentist> addDentist(@RequestBody Dentist dentist) throws ResourceNotFoundException {
        Dentist serviceResponse = dentistService.addDentist(dentist);
        return new ResponseEntity<>(serviceResponse,HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteDentist")
    public ResponseEntity<Dentist> deleteDentist(@RequestParam Long id) throws ResourceNotFoundException {
        Dentist serviceResponse = dentistService.deleteDentist(id);
        return new ResponseEntity<>(serviceResponse,HttpStatus.ACCEPTED);
    }

    @GetMapping("/searchDentist")
    public <T> ResponseEntity<List<Dentist>> findDentist(@RequestParam String info){
        List<Dentist> dentistList = dentistService.findDentist(info);
        return new ResponseEntity<>(dentistList,HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Dentist> updateDentist(@RequestBody Dentist dentist) throws AlreadyExistsException, JsonMappingException, ResourceNotFoundException {
        Dentist serviceResponse = dentistService.modifyDentist(dentist);
        return new ResponseEntity<>(serviceResponse,HttpStatus.OK);
    }

    @GetMapping("/listAllDentists")
    public ResponseEntity<List<Dentist>> listDentists() throws ResourceNotFoundException {
        List<Dentist> serviceResponse = dentistService.listDentists();
            return new ResponseEntity<>(serviceResponse,HttpStatus.OK);
    }

    @GetMapping("/listDentistAppointments")
    public ResponseEntity<List<AppointmentDTO>> listAppointments(@RequestParam Integer licenseNumber) throws ResourceNotFoundException {
        List<AppointmentDTO> serviceResponse = dentistService.listAppointments(licenseNumber);
            return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @DeleteMapping("/deletePatient")
    public ResponseEntity<String> deletePatient(@RequestParam Long id) throws ResourceNotFoundException {
        Patient serviceResponse = patientService.deletePatient(id);
        return new ResponseEntity<>("The patient has been deleted", HttpStatus.ACCEPTED);
    }
    @GetMapping("/searchPatient")
    public <T> ResponseEntity<List<PatientDTO>> findPatients(@RequestParam String info) {
        List<PatientDTO> patientList = patientService.findPatient(info);
        if (patientList.isEmpty()) {
            return new ResponseEntity<>(patientList, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(patientList, HttpStatus.OK);
        }
    }
    @GetMapping("/listAllPatients")
    public ResponseEntity<List<PatientDTO>> listPatients() throws ResourceNotFoundException {
        List<PatientDTO> serviceResponse = patientService.listPatients();
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @GetMapping("/listPatientAppointments")
    public ResponseEntity<List<AppointmentDTO>> listAppointments(@RequestParam Long idCard) throws ResourceNotFoundException{
        List<AppointmentDTO> serviceResponse = patientService.listAppointments(idCard);
        if (serviceResponse.isEmpty()) {
            return new ResponseEntity<>(serviceResponse, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/pastAppointmentsPatient")
    public ResponseEntity<List<PastAppointmentMongo>> listPatientPastAppointments(@RequestParam Long idCard) throws ResourceNotFoundException {
        List<PastAppointmentMongo> serviceResponse = pastAppointmentServiceMongo.listPatientPastAppointments(idCard);
        return new ResponseEntity<>(serviceResponse,HttpStatus.OK);
    }


    @GetMapping("/pastAppointmentsDentist")
    public ResponseEntity<List<PastAppointmentMongo>> listDentistPastAppointments(@RequestParam Integer licenseNumber) throws ResourceNotFoundException {
        List<PastAppointmentMongo> serviceResponse = pastAppointmentServiceMongo.listDentistPastAppointments(licenseNumber);
        return new ResponseEntity<>(serviceResponse,HttpStatus.OK);
    }


}
