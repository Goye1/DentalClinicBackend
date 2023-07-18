package com.DentalClinicX.DentalClinicManagement.controller.data;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Dentist;
import com.DentalClinicX.DentalClinicManagement.service.AuthenticationService;
import com.DentalClinicX.DentalClinicManagement.service.DentistService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AdminDataLoader implements ApplicationRunner {



    private final AuthenticationService authenticationService;

    private final DentistService dentistService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        authenticationService.registerAdmin(
                RegisterRequest.builder()
                        .firstname("John")
                        .lastname("Java")
                        .email("admin")
                        .password("admin1234")
                        .build());

        dentistService.addDentist(new Dentist("Dr John","Sturgeon",214235));

        }
    }