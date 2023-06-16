package com.DentalClinicX.DentalClinicManagement.service;

import com.DentalClinicX.DentalClinicManagement.model.dto.DentistDTO;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Dentist;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Patient;
import com.DentalClinicX.DentalClinicManagement.persistance.repository.IAppointmentRepository;
import com.DentalClinicX.DentalClinicManagement.persistance.repository.IDentistRepository;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DentistService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Logger logger;
    private final IDentistRepository dentistRepository;
    private final IAppointmentRepository appointmentRepository;

    @Autowired
    public DentistService(IDentistRepository dentistRepository,IAppointmentRepository appointmentRepository) {
        this.dentistRepository = dentistRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public List<DentistDTO> listDentists() {
        List<DentistDTO> dentistDTOList = null;
        try {
            List<Dentist> dentists = dentistRepository.findAll();
            dentistDTOList = new ArrayList<>();
            for (Dentist dentist : dentists) {
                dentistDTOList.add(objectMapper.convertValue(dentist, DentistDTO.class));
            }
        } catch (DataAccessException e) {
            logger.fatal("Error trying to list all the dentists", e.getCause());
        }
        return dentistDTOList;
    }

    public Dentist addDentist(Dentist dentist) {
        Dentist d = null;
        try {
            for (Dentist p:dentistRepository.findAll()) {
                assert !Objects.equals(p.getLicenseNumber(), dentist.getLicenseNumber());
            }
            dentistRepository.save(dentist);
            if (dentistRepository.existsById(dentist.getId())) {
                d = dentist;
                logger.info("A new dentist named: " + dentist.getName() + " " + dentist.getSurname() + " has been added.");
            }
        } catch (DataAccessException e) {
            logger.fatal("Error attempting to add the dentist: " + dentist.getName() + " " + dentist.getSurname(), e.getCause());
        }catch (AssertionError e){
            logger.fatal("A dentist with that Id card already exists",e.getCause());
        }
        return d;
    }

    public Dentist modifyDentist(Dentist modifiedDentist) {
        Dentist existingDentist = null;
        try {
            existingDentist = dentistRepository.findById(modifiedDentist.getId()).orElse(null);
            assert existingDentist != null;
            objectMapper.updateValue(existingDentist, modifiedDentist);
            for (Dentist p:dentistRepository.findAll()) {
                assert !Objects.equals(p.getLicenseNumber(), modifiedDentist.getLicenseNumber());
            }
            dentistRepository.save(existingDentist);
            logger.info("The dentist " + existingDentist.getName() + " " + existingDentist.getSurname() + " has been modified");
        } catch (DataAccessException e) {
            logger.fatal("Error attempting to modify a dentist", e.getCause());
        } catch (AssertionError e) {
            logger.fatal("A dentist with that Id card already exists",e.getCause());
        } catch (JsonMappingException e) {
            logger.error(e.getCause());
            throw new RuntimeException(e);
        }
        return existingDentist;
    }

    public Dentist deleteDentist(Long id) {
        Dentist d = null;
        try {
            d = dentistRepository.findById(id).orElse(null);
            assert d != null;
            dentistRepository.delete(d);
            logger.info("The dentist " + d.getName() + " " + d.getSurname() + " has been deleted");
        }catch (AssertionError e){
            logger.error("Dentist not found", e.getCause());
        }catch (DataAccessException e){
            logger.fatal("Error attempting to modify a dentist", e.getCause());
        }
        return d;
    }

    public <T> List<Dentist> findDentist(T info) {
        List<Dentist> dentistList = dentistRepository.findAll();
        List<Dentist> dentists = new ArrayList<>();
        for (Dentist dentist : dentistList) {
            if (dentist.getName().toLowerCase().contains((CharSequence) info) || dentist.getSurname().toLowerCase().contains((CharSequence) info)
                    || dentist.getLicenseNumber().toString().contains((CharSequence) info)) {
                dentists.add(dentist);
            }
        }
        return dentists;
    }

    public Dentist findById(Long id){
        Dentist dentist = null;
        try {
            dentist = dentistRepository.findById(id).orElse(null);
        }catch (DataAccessException e){
            logger.fatal("Error trying to find a patient by id", e.getCause());
        }
        return dentist;
    }
}
