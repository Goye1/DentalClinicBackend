package com.DentalClinicX.DentalClinicManagement.service;
import com.DentalClinicX.DentalClinicManagement.exceptions.AlreadyExistsException;
import com.DentalClinicX.DentalClinicManagement.exceptions.ResourceNotFoundException;
import com.DentalClinicX.DentalClinicManagement.model.dto.AppointmentDTO;
import com.DentalClinicX.DentalClinicManagement.model.dto.DentistDTO;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Appointment;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Dentist;
import com.DentalClinicX.DentalClinicManagement.persistance.repository.IDentistRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DentistService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Logger logger;
    private final IDentistRepository dentistRepository;

    @Autowired
    public DentistService(IDentistRepository dentistRepository) {
        this.dentistRepository = dentistRepository;
    }

    public List<DentistDTO> listDentists() throws ResourceNotFoundException {
        List<DentistDTO> dentistDTOList = null;
            List<Dentist> dentists = dentistRepository.findAll();
            dentistDTOList = new ArrayList<>();
            for (Dentist dentist : dentists) {
                dentistDTOList.add(objectMapper.convertValue(dentist, DentistDTO.class));
            }
            if(dentistDTOList.isEmpty()){
                throw new ResourceNotFoundException("No dentists were found");
            }
        return dentistDTOList;
    }

    public Dentist addDentist(Dentist dentist) throws ResourceNotFoundException {
            if (dentistRepository.existsBylicenseNumber(dentist.getLicenseNumber())) {
                throw new ResourceNotFoundException("A dentist with " + dentist.getLicenseNumber() + " license number already exists");
            }
                dentistRepository.save(dentist);
                logger.info("A new dentist named: " + dentist.getName() + " " + dentist.getSurname() + " has been added.");
        return dentist;
    }

    public Dentist modifyDentist(Dentist modifiedDentist) throws JsonMappingException, ResourceNotFoundException, AlreadyExistsException {
            Dentist existingDentist = dentistRepository.findById(modifiedDentist.getId()).orElseThrow(() -> new ResourceNotFoundException("Dentist to modify does not exist"));
                if (!dentistRepository.existsBylicenseNumber(modifiedDentist.getLicenseNumber()) || Objects.equals(modifiedDentist.getLicenseNumber(), existingDentist.getLicenseNumber())) {
                    objectMapper.updateValue(existingDentist, modifiedDentist);
                    dentistRepository.save(existingDentist);
                    logger.info("The dentist " + existingDentist.getName() + " " + existingDentist.getSurname() + " has been modified");
                }else {
                    throw new AlreadyExistsException("A dentist with " + modifiedDentist.getLicenseNumber() + " license number already exists");
            }
        return modifiedDentist;
    }

    public Dentist deleteDentist(Long id) throws ResourceNotFoundException {
           Dentist d = dentistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No dentist was found to delete with de id: " + id));
                dentistRepository.delete(d);
                logger.info("The dentist " + d.getName() + " " + d.getSurname() + " has been deleted");
        return d;
    }

    public List<Dentist> findDentist(String info) {
        if (info == null || info.trim().isEmpty() || !info.matches("^[a-zA-Z0-9]*$")) {
            throw new IllegalArgumentException("The submitted data is not valid");
        }
        List<Dentist> dentistList = dentistRepository.findAll();
        List<Dentist> dentists = new ArrayList<>();

        for (Dentist dentist : dentistList) {
            String name = dentist.getName();
            String surname = dentist.getSurname();
            String licenseNumber = dentist.getLicenseNumber().toString();

            if (name.toLowerCase().startsWith(info.toLowerCase().substring(0, 1))
                    || surname.toLowerCase().startsWith(info.toLowerCase().substring(0, 1))
                    || licenseNumber.startsWith(info)) {
                dentists.add(dentist);
            }
        }
        dentists.sort(Comparator.comparing(dentist -> dentist.getName().substring(0, 1)));
        return dentists;
    }

    public Dentist findById(Long id) throws ResourceNotFoundException {
           return dentistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Dentist with id: " + id +"not found"));
    }

    public Dentist findByLicenseNumber(Integer licenseNumber) throws ResourceNotFoundException {
        Dentist dentist = dentistRepository.findBylicenseNumber(licenseNumber);
        if(dentist == null){
            throw new ResourceNotFoundException("Dentist with license number: " + licenseNumber + " dosent exist");
        }
        return dentist;
    }

    public List<AppointmentDTO> listAppointments(Integer idCard) throws ResourceNotFoundException {
        List<AppointmentDTO> appointmentDTOList = null;
        Dentist foundDentist = this.findByLicenseNumber(idCard);
                Set<Appointment> appointmentHashSet = foundDentist.getAppointments();
                appointmentDTOList = new ArrayList<>();
                for (Appointment a : appointmentHashSet) {
                    appointmentDTOList.add(objectMapper.convertValue(a, AppointmentDTO.class));
                }
        return appointmentDTOList;
    }
}
