package com.DentalClinicX.DentalClinicManagement.controller.data;

import com.DentalClinicX.DentalClinicManagement.persistance.entity.Patient;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;
    private Patient patient;
    private Role role;
}
