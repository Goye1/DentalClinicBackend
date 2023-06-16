package com.DentalClinicX.DentalClinicManagement.controller;

import com.DentalClinicX.DentalClinicManagement.model.dto.DentistDTO;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Dentist;
import com.DentalClinicX.DentalClinicManagement.service.DentistService;
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
    public ResponseEntity<Dentist> addDentist(@RequestBody Dentist dentist){
        Dentist serviceResponse = dentistService.addDentist(dentist);
        if(serviceResponse == null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(serviceResponse, HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/dentists/delete")
    public ResponseEntity<String> deleteDentist(@RequestParam Long id){
        Dentist serviceResponse = dentistService.deleteDentist(id);
        if(serviceResponse == null){
            return new ResponseEntity<>("Dentist not found",HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>("The dentist has been deleted",HttpStatus.OK);
        }
    }

    @GetMapping("/dentists/search")
    public <T> ResponseEntity<List<Dentist>> findDentist(@RequestParam T info){
        List<Dentist> dentistList = dentistService.findDentist(info);
        if(dentistList.isEmpty()){
            return new ResponseEntity<>(dentistList,HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(dentistList,HttpStatus.OK);
        }
    }

    @PutMapping("dentists/update")
    public ResponseEntity<Dentist> updateDentist(@RequestBody Dentist dentist){
        Dentist serviceResponse = dentistService.modifyDentist(dentist);
        if(serviceResponse == null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(serviceResponse,HttpStatus.OK);
        }
    }

    @GetMapping("dentists/listAll")
    public ResponseEntity<List<DentistDTO>> listPatients(){
        List<DentistDTO> serviceResponse = dentistService.listDentists();
        if(serviceResponse == null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(serviceResponse,HttpStatus.OK);
        }
    }

}
