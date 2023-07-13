package com.DentalClinicX.DentalClinicManagement.controller;
import com.DentalClinicX.DentalClinicManagement.exceptions.AlreadyExistsException;
import com.DentalClinicX.DentalClinicManagement.exceptions.ResourceNotFoundException;
import com.DentalClinicX.DentalClinicManagement.model.dto.AppointmentDTO;
import com.DentalClinicX.DentalClinicManagement.model.dto.DentistDTO;
import com.DentalClinicX.DentalClinicManagement.model.wrapper.AppointmentWrapper;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Dentist;
import com.DentalClinicX.DentalClinicManagement.persistance.entityMongo.PastAppointmentMongo;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Appointment;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Patient;
import com.DentalClinicX.DentalClinicManagement.service.DentistService;
import com.DentalClinicX.DentalClinicManagement.service.PastAppointmentServiceMongo;
import com.DentalClinicX.DentalClinicManagement.service.PatientService;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/patients")
public class PatientUserController {
    private final PatientService patientService;

    private final DentistService dentistService;
    private final PastAppointmentServiceMongo pastAppointmentServiceMongo;

    @Autowired
    public PatientUserController(PatientService patientService, DentistService dentistService, PastAppointmentServiceMongo pastAppointmentServiceMongo) {
        this.patientService = patientService;
        this.dentistService = dentistService;
        this.pastAppointmentServiceMongo = pastAppointmentServiceMongo;
    }

    @PutMapping("/update")
    public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient) throws AlreadyExistsException, JsonMappingException, ResourceNotFoundException {
        Patient serviceResponse = patientService.modifyPatient(patient);
            return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }

    @PostMapping("/addAppointment")
    public ResponseEntity<Appointment> addAppointment(@RequestBody AppointmentWrapper appointment) throws ResourceNotFoundException {
        Appointment serviceResponse = patientService.addAppointment(appointment);
            return new ResponseEntity<>(serviceResponse,HttpStatus.CREATED);
    }

    @GetMapping("/listAppointments")
    public ResponseEntity<List<AppointmentDTO>> listAppointments(@RequestParam Long idCard) throws ResourceNotFoundException{
        List<AppointmentDTO> serviceResponse = patientService.listAppointments(idCard);
        if (serviceResponse.isEmpty()) {
            return new ResponseEntity<>(serviceResponse, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
        }
    }

    @DeleteMapping("/deleteAppointment")
    public ResponseEntity<String> deleteAppointment(@RequestParam Long id) throws ResourceNotFoundException {
        String serviceResponse = patientService.deleteAppointment(id);
        return new ResponseEntity<>(serviceResponse,HttpStatus.OK);
    }

    @GetMapping("/pastAppointments")
    public ResponseEntity<List<PastAppointmentMongo>> listPatientPastAppointments(@RequestParam Long idCard) throws ResourceNotFoundException {
        List<PastAppointmentMongo> serviceResponse = pastAppointmentServiceMongo.listPatientPastAppointments(idCard);
        return new ResponseEntity<>(serviceResponse,HttpStatus.OK);
    }

    @GetMapping("/searchDentist")
    public <T> ResponseEntity<List<Dentist>> findDentist(@RequestParam String info){
        List<Dentist> dentistList = dentistService.findDentist(info);
        return new ResponseEntity<>(dentistList,HttpStatus.OK);
    }

    @GetMapping("/listAllDentists")
    public ResponseEntity<List<DentistDTO>> listDentists() throws ResourceNotFoundException {
        List<DentistDTO> serviceResponse = dentistService.listDentists();
        return new ResponseEntity<>(serviceResponse,HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<Patient> getPatientInfo(@RequestParam String email) throws ResourceNotFoundException {
        Patient patient = patientService.findByEmail(email);
        return new ResponseEntity<>(patient,HttpStatus.OK);
    }


}
