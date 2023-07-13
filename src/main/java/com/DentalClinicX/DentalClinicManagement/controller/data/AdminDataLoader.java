package com.DentalClinicX.DentalClinicManagement.controller.data;

import com.DentalClinicX.DentalClinicManagement.persistance.entity.Address;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Role;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.User;
import com.DentalClinicX.DentalClinicManagement.persistance.repository.IAddressRepository;
import com.DentalClinicX.DentalClinicManagement.persistance.repository.IAppointmentRepository;
import com.DentalClinicX.DentalClinicManagement.persistance.repository.IUserRepository;
import com.DentalClinicX.DentalClinicManagement.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AdminDataLoader implements ApplicationRunner {

   private final IUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationService authenticationService;

    private final IAddressRepository addressRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(authenticationService.registerAdmin(
                RegisterRequest.builder()
                        .firstname("John")
                        .lastname("Java")
                        .email("admin")
                        .password("admin1234")
                        .build()
        ));
        }
    }