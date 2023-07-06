package com.DentalClinicX.DentalClinicManagement.controller;
import com.DentalClinicX.DentalClinicManagement.exceptions.AlreadyExistsException;
import com.DentalClinicX.DentalClinicManagement.exceptions.ResourceNotFoundException;
import com.DentalClinicX.DentalClinicManagement.model.dto.AppointmentDTO;
import com.DentalClinicX.DentalClinicManagement.model.dto.DentistDTO;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Dentist;
import com.DentalClinicX.DentalClinicManagement.service.DentistService;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DentistController {

    private final DentistService dentistService;

    @Autowired
    public DentistController(DentistService dentistService) {
        this.dentistService = dentistService;
    }

    @PostMapping("/dentists/add")
    public ResponseEntity<Dentist> addDentist(@RequestBody Dentist dentist) throws ResourceNotFoundException {
        Dentist serviceResponse = dentistService.addDentist(dentist);
        return new ResponseEntity<>(serviceResponse,HttpStatus.CREATED);
    }

    @DeleteMapping("/dentists/delete")
    public ResponseEntity<Dentist> deleteDentist(@RequestParam Long id) throws ResourceNotFoundException {
        Dentist serviceResponse = dentistService.deleteDentist(id);
        return new ResponseEntity<>(serviceResponse,HttpStatus.ACCEPTED);
    }

    @GetMapping("/dentists/search")
    public <T> ResponseEntity<List<Dentist>> findDentist(@RequestParam String info){
        List<Dentist> dentistList = dentistService.findDentist(info);
        return new ResponseEntity<>(dentistList,HttpStatus.OK);
    }

    @PutMapping("dentists/update")
    public ResponseEntity<Dentist> updateDentist(@RequestBody Dentist dentist) throws AlreadyExistsException, JsonMappingException, ResourceNotFoundException {
        Dentist serviceResponse = dentistService.modifyDentist(dentist);
        return new ResponseEntity<>(serviceResponse,HttpStatus.OK);
    }

    @GetMapping("dentists/listAll")
    public ResponseEntity<List<DentistDTO>> listPatients() throws ResourceNotFoundException {
        List<DentistDTO> serviceResponse = dentistService.listDentists();
            return new ResponseEntity<>(serviceResponse,HttpStatus.OK);
    }

    @GetMapping("dentists/listAppointments")
    public ResponseEntity<List<AppointmentDTO>> listAppointments(@RequestParam Integer licenseNumber) throws ResourceNotFoundException {
        List<AppointmentDTO> serviceResponse = dentistService.listAppointments(licenseNumber);
            return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }
}
