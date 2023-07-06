package com.DentalClinicX.DentalClinicManagement.controller;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Address;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Patient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



@SpringBootTest
@AutoConfigureMockMvc
public
class PatientControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
     void addPatientAPI() throws Exception {
        Address address = new Address("Hollee",324,"ny","noidea");
        Patient patient = new Patient("Juan","Senna",42424,address);
        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE,false)
                .writer();
        String payloadJson = writer.writeValueAsString(patient);

        mvc.perform(MockMvcRequestBuilders.post("/patients/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }




}
