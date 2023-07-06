package com.DentalClinicX.DentalClinicManagement.controller;
import com.DentalClinicX.DentalClinicManagement.exceptions.AlreadyExistsException;
import com.DentalClinicX.DentalClinicManagement.exceptions.ResourceNotFoundException;
import com.DentalClinicX.DentalClinicManagement.model.dto.AppointmentDTO;
import com.DentalClinicX.DentalClinicManagement.model.dto.PatientDTO;
import com.DentalClinicX.DentalClinicManagement.model.wrapper.AppointmentWrapper;
import com.DentalClinicX.DentalClinicManagement.persistance.repositoryMongo.IAppointmentRepositoryMongo;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Address;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Appointment;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Patient;
import com.DentalClinicX.DentalClinicManagement.service.PatientService;
import com.fasterxml.jackson.databind.JsonMappingException;
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
    @PostMapping("/landing-page/add")
    public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) throws AlreadyExistsException {
        Address address = patient.getAddress();
        Patient serviceResponse = patientService.addPatient(patient, address);
       return new ResponseEntity<>(serviceResponse,HttpStatus.CREATED);
    }

    @DeleteMapping("/patients/delete")
    public ResponseEntity<String> deletePatient(@RequestParam Long id) throws ResourceNotFoundException {
        Patient serviceResponse = patientService.deletePatient(id);
        return new ResponseEntity<>("The patient has been deleted", HttpStatus.ACCEPTED);
    }

    @GetMapping("/patients/search")
    public <T> ResponseEntity<List<PatientDTO>> findPatients(@RequestParam String info) {
        List<PatientDTO> patientList = patientService.findPatient(info);
        if (patientList.isEmpty()) {
            return new ResponseEntity<>(patientList, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(patientList, HttpStatus.OK);
        }
    }

    @PutMapping("patients/update")
    public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient) throws AlreadyExistsException, JsonMappingException, ResourceNotFoundException {
        Patient serviceResponse = patientService.modifyPatient(patient);
            return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @GetMapping("patients/listAll")
    public ResponseEntity<List<PatientDTO>> listPatients() throws ResourceNotFoundException {
        List<PatientDTO> serviceResponse = patientService.listPatients();
            return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @PostMapping("patients/addAppointment")
    public ResponseEntity<Appointment> addAppointment(@RequestBody AppointmentWrapper appointment) throws ResourceNotFoundException {
        Appointment serviceResponse = patientService.addAppointment(appointment);
            return new ResponseEntity<>(serviceResponse,HttpStatus.CREATED);
    }

    @GetMapping("patients/listAppointments")
    public ResponseEntity<List<AppointmentDTO>> listAppointments(@RequestParam Long idCard) throws ResourceNotFoundException{
        List<AppointmentDTO> serviceResponse = patientService.listAppointments(idCard);
        if (serviceResponse.isEmpty()) {
            return new ResponseEntity<>(serviceResponse, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
        }
    }

    @DeleteMapping("patients/deleteAppointment")
    public ResponseEntity<String> deleteAppointment(@RequestParam Long id) throws ResourceNotFoundException {
        String serviceResponse = patientService.deleteAppointment(id);
        return new ResponseEntity<>(serviceResponse,HttpStatus.OK);
    }
}
