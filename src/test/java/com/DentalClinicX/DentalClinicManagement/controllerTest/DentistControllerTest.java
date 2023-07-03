package com.DentalClinicX.DentalClinicManagement.controllerTest;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Dentist;
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
public class DentistControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void addDentistAPI() throws Exception {
        Dentist dentist = new Dentist("Frank","Queries",43425);
        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE,false)
                .writer();
        String payloadJson = writer.writeValueAsString(dentist);

        mvc.perform(MockMvcRequestBuilders.post("/dentists/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
