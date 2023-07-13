package com.DentalClinicX.DentalClinicManagement.service;
import com.DentalClinicX.DentalClinicManagement.controller.data.AuthenticationRequest;
import com.DentalClinicX.DentalClinicManagement.controller.data.AuthenticationResponse;
import com.DentalClinicX.DentalClinicManagement.controller.data.PatientRequest;
import com.DentalClinicX.DentalClinicManagement.controller.data.RegisterRequest;
import com.DentalClinicX.DentalClinicManagement.exceptions.AlreadyExistsException;
import com.DentalClinicX.DentalClinicManagement.exceptions.ResourceNotFoundException;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Address;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Patient;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Role;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.User;
import com.DentalClinicX.DentalClinicManagement.persistance.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final PatientService patientService;

    public AuthenticationResponse register(RegisterRequest request) throws AlreadyExistsException {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new AlreadyExistsException("A user is already registered with that email.");
        }
        Address address = request.getAddress();
        Patient patient = new Patient(request.getFirstname(),request.getLastname(),request.getIdCard(),address);
        patient.setEmail(request.getEmail());

        patientService.addPatient(patient,address);

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) throws ResourceNotFoundException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        Patient patient = null;
        if(user.getRole() == Role.USER){
            patient = patientService.findByEmail(request.getEmail());
            patient.setDischargeDate(LocalDate.of(2024,8,31));
        }
        return AuthenticationResponse.builder()
                .patient(patient)
                .role(user.getRole())
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse registerAdmin(RegisterRequest request) throws AlreadyExistsException {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
