package com.DentalClinicX.DentalClinicManagement.service;
import com.DentalClinicX.DentalClinicManagement.model.dto.AppointmentDTO;
import com.DentalClinicX.DentalClinicManagement.model.dto.PatientDTO;
import com.DentalClinicX.DentalClinicManagement.model.wrapper.AppointmentWrapper;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Address;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Appointment;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Dentist;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Patient;
import com.DentalClinicX.DentalClinicManagement.persistance.repository.IAddressRepository;
import com.DentalClinicX.DentalClinicManagement.persistance.repository.IAppointmentRepository;
import com.DentalClinicX.DentalClinicManagement.persistance.repository.IPatientRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class PatientService {
    private final IAddressRepository addressRepository;
    private final IPatientRepository patientRepository;
    private final IAppointmentRepository appointmentRepository;
    private final DentistService dentistService;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Logger logger;

    @Autowired
    public PatientService(IPatientRepository patientRepository, IAddressRepository addressRepository, IAppointmentRepository appointmentRepository, DentistService dentistService) {
        this.patientRepository = patientRepository;
        this.addressRepository = addressRepository;
        this.appointmentRepository = appointmentRepository;
        this.dentistService = dentistService;
    }

    public List<PatientDTO> listPatients() {
        List<PatientDTO> patientDTOList = null;
        try {
            List<Patient> patients = patientRepository.findAll();
            patientDTOList = new ArrayList<>();
            for (Patient patient : patients) {
                patientDTOList.add(objectMapper.convertValue(patient, PatientDTO.class));
            }
        } catch (DataAccessException e) {
            logger.fatal("Error trying to list all the patients", e.getCause());
        }
        return patientDTOList;
    }

    public Patient addPatient(Patient patient, Address address) {
        Patient p = null;
        try {
            if (patientRepository.existsByidCard(patient.getIdCard())) {
                return null;
            }
            patient.setAddress(address);
            addressRepository.save(address);
            patientRepository.save(patient);
            if (patientRepository.existsById(patient.getId())) {
                p = patient;
                logger.info("A new patient named: " + patient.getName() + " " + patient.getSurname() + " has been added.");
            }
        } catch (DataAccessException e) {
            logger.fatal("Error attempting to add the patient: " + patient.getName() + " " + patient.getSurname(), e.getCause());
        } catch (AssertionError e) {
            logger.fatal("A patient with that Id card already exists", e.getCause());
        }
        return p;
    }

    public <T> List<Patient> findPatient(T info) {
        List<Patient> patientList = patientRepository.findAll();
        List<Patient> patients = new ArrayList<>();
        for (Patient patient : patientList) {
            if (patient.getName().toLowerCase().contains((CharSequence) info) || patient.getSurname().toLowerCase().contains((CharSequence) info)
                    || patient.getId().toString().contains((CharSequence) info)) {
                patients.add(patient);
            }
        }
        return patients;
    }

    public Patient modifyPatient(Patient modifiedPatient) {
        Patient existingPatient = null;
        try {
            existingPatient = patientRepository.findById(modifiedPatient.getId()).orElse(null);
            if(existingPatient != null){
            if (!patientRepository.existsByidCard(modifiedPatient.getIdCard()) || existingPatient.getIdCard() == modifiedPatient.getIdCard()) {
                objectMapper.updateValue(existingPatient, modifiedPatient);
                patientRepository.save(existingPatient);
                logger.info("The patient " + existingPatient.getName() + " " + existingPatient.getSurname() + " has been modified");
            }}
        } catch (DataAccessException e) {
            logger.fatal("Error attempting to modify a patient", e.getCause());
        } catch (JsonMappingException e) {
            logger.error(e.getCause());
        }
        return existingPatient;
    }

    public Patient deletePatient(Long id) {
        Patient p = null;
        try {
            p = patientRepository.findById(id).orElse(null);
            if(p != null){
            patientRepository.delete(p);
            logger.info("The patient " + p.getName() + " " + p.getSurname() + " has been deleted");}
        } catch (DataAccessException e) {
            logger.fatal("Error trying to modify a patient", e.getCause());
        }
        return p;
    }

    public Patient findById(Long id) {
        Patient patient = null;
        try {
            patient = patientRepository.findById(id).orElse(null);
        } catch (DataAccessException e) {
            logger.fatal("Error trying to find a patient by id", e.getCause());
        }
        return patient;
    }

    public Appointment addAppointment(AppointmentWrapper appointment) {
        Appointment realAppointment = null;
        try {
            Dentist dentist = dentistService.findById(appointment.getDentist_id());
            Patient patient = this.findById(appointment.getPatient_id());
            if (dentist != null && patient != null) {
                realAppointment = new Appointment();
                realAppointment.setDentist(dentist);
                realAppointment.setPatient(patient);
                realAppointment.setAppointmentDate(appointment.getAppointmentDate());
                patient.getAppointments().add(realAppointment);
                dentist.getAppointments().add(realAppointment);
                appointmentRepository.save(realAppointment);
            }
        } catch (DataAccessException e) {
            logger.fatal("Error trying to add an appointment", e.getCause());
        }
        return realAppointment;
    }

    public List<AppointmentDTO> listAppointments(Long id) {
        List<AppointmentDTO> appointmentDTOList = null;
        Patient foundPatient = null;
        try {
            foundPatient = this.findById(id);
            if (foundPatient != null) {
                Set<Appointment> appointmentHashSet = foundPatient.getAppointments();
                appointmentDTOList = new ArrayList<>();
                for (Appointment a : appointmentHashSet) {
                    appointmentDTOList.add(objectMapper.convertValue(a, AppointmentDTO.class));
                }
            }
        } catch (DataAccessException e) {
            assert foundPatient != null;
            logger.fatal("Error trying to list all the appointments for the patient: " + foundPatient.getName() + " " + foundPatient.getSurname(), e.getCause());
        }
        return appointmentDTOList;
    }
}

