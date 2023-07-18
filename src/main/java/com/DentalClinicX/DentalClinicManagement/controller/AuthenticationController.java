package com.DentalClinicX.DentalClinicManagement.controller;


import com.DentalClinicX.DentalClinicManagement.controller.data.AuthenticationRequest;
import com.DentalClinicX.DentalClinicManagement.controller.data.AuthenticationResponse;
import com.DentalClinicX.DentalClinicManagement.controller.data.RegisterRequest;
import com.DentalClinicX.DentalClinicManagement.exceptions.AlreadyExistsException;
import com.DentalClinicX.DentalClinicManagement.exceptions.ResourceNotFoundException;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Patient;
import com.DentalClinicX.DentalClinicManagement.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/landing-page")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    ResponseEntity<AuthenticationResponse> registerAndCreatePatient(@RequestBody RegisterRequest request) throws AlreadyExistsException {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) throws ResourceNotFoundException {
        return ResponseEntity.ok(authenticationService.login(request));
    }



}
