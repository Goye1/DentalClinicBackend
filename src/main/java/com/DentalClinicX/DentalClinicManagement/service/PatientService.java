package com.DentalClinicX.DentalClinicManagement.service;
import com.DentalClinicX.DentalClinicManagement.exceptions.AlreadyExistsException;
import com.DentalClinicX.DentalClinicManagement.exceptions.ResourceNotFoundException;
import com.DentalClinicX.DentalClinicManagement.model.dto.AppointmentDTO;
import com.DentalClinicX.DentalClinicManagement.model.dto.PatientDTO;
import com.DentalClinicX.DentalClinicManagement.model.wrapper.AppointmentWrapper;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Address;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Appointment;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Dentist;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Patient;
import com.DentalClinicX.DentalClinicManagement.persistance.entityMongo.DischargedPatientMongo;
import com.DentalClinicX.DentalClinicManagement.persistance.repository.IAddressRepository;
import com.DentalClinicX.DentalClinicManagement.persistance.repository.IAppointmentRepository;
import com.DentalClinicX.DentalClinicManagement.persistance.repository.IPatientRepository;
import com.DentalClinicX.DentalClinicManagement.persistance.repositoryMongo.IPatientRepositoryMongo;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PatientService {
    private final IAddressRepository addressRepository;
    private final IPatientRepository patientRepository;

    private final IPatientRepositoryMongo patientRepositoryMongo;
    private final IAppointmentRepository appointmentRepository;
    private final DentistService dentistService;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Logger logger;

    @Autowired
    public PatientService(IPatientRepository patientRepository, IAddressRepository addressRepository, IPatientRepositoryMongo patientRepositoryMongo, IAppointmentRepository appointmentRepository, DentistService dentistService) {
        this.patientRepository = patientRepository;
        this.addressRepository = addressRepository;
        this.patientRepositoryMongo = patientRepositoryMongo;
        this.appointmentRepository = appointmentRepository;
        this.dentistService = dentistService;
    }

    public List<PatientDTO> listPatients() {
        List<PatientDTO> patientDTOList = null;
            List<Patient> patients = patientRepository.findAll();
            List<DischargedPatientMongo> dischargedPatientMongos = patientRepositoryMongo.findAll();
            patientDTOList = new ArrayList<>();
            for (Patient patient : patients) {
                PatientDTO pDTO = objectMapper.convertValue(patient, PatientDTO.class);
                pDTO.setDischarged(false);
                patientDTOList.add(pDTO);
            }
            for (DischargedPatientMongo dischargedPatientMongo : dischargedPatientMongos){
                PatientDTO pDTOMongo = objectMapper.convertValue(dischargedPatientMongo, PatientDTO.class);
                pDTOMongo.setDischarged(true);
                patientDTOList.add(pDTOMongo);
            }
        return patientDTOList;
    }

    public Patient addPatient(Patient patient, Address address) throws AlreadyExistsException {
            if (patientRepository.existsByidCard(patient.getIdCard()) || patientRepositoryMongo.existsByidCard(patient.getIdCard())) {
                throw new AlreadyExistsException("A patient with the id card: " + patient.getIdCard() + " already exists");
            }
            patient.setAddress(address);
            addressRepository.save(address);
            patientRepository.save(patient);
            logger.info("A new patient named: " + patient.getName() + " " + patient.getSurname() + " has been added.");
        return patient;
    }

    public List<PatientDTO> findPatient(String info) {
        if (info == null || info.trim().isEmpty() || !info.matches("^[a-zA-Z0-9]*$")) {
            throw new IllegalArgumentException("The submitted data is not valid");
        }
        List<PatientDTO> patientDTOList = new ArrayList<>();
        List<DischargedPatientMongo> dischargedPatientMongoList = patientRepositoryMongo.findAll();
        List<Patient> patientList = patientRepository.findAll();

        for (Patient patient : patientList) {
            String name = patient.getName();
            String surname = patient.getSurname();
            String id = patient.getId().toString();
            if (name.toLowerCase().startsWith(info.toLowerCase().substring(0, 1))
                    || surname.toLowerCase().startsWith(info.toLowerCase().substring(0, 1))
                    || id.startsWith(info)) {
                PatientDTO patientDTO = objectMapper.convertValue(patient,PatientDTO.class);
                patientDTO.setDischarged(false);
                patientDTOList.add(patientDTO);
            }
        }
        for(DischargedPatientMongo dischargedPatientMongo : dischargedPatientMongoList){
            String name = dischargedPatientMongo.getName();
            String surname = dischargedPatientMongo.getSurname();
            String id = dischargedPatientMongo.getId().toString();
            if (name.toLowerCase().startsWith(info.toLowerCase().substring(0, 1))
                    || surname.toLowerCase().startsWith(info.toLowerCase().substring(0, 1))
                    || id.startsWith(info)) {
                PatientDTO patientDTO = objectMapper.convertValue(dischargedPatientMongo,PatientDTO.class);
                patientDTO.setDischarged(true);
                patientDTOList.add(patientDTO);
            }
        }
        patientDTOList.sort(Comparator.comparing(patient -> patient.getName().substring(0, 1)));
        return patientDTOList;
    }

    public Patient modifyPatient(Patient modifiedPatient) throws AlreadyExistsException, ResourceNotFoundException, JsonMappingException{
           Patient existingPatient = patientRepository.findById(modifiedPatient.getId()).orElseThrow(() -> new ResourceNotFoundException("Patient to modify does not exist"));
            if (!patientRepository.existsByidCard(modifiedPatient.getIdCard()) && !patientRepositoryMongo.existsByidCard(modifiedPatient.getIdCard()) || existingPatient.getIdCard() == modifiedPatient.getIdCard()) {
                objectMapper.updateValue(existingPatient, modifiedPatient);
                patientRepository.save(existingPatient);
                logger.info("The patient " + existingPatient.getName() + " " + existingPatient.getSurname() + " has been modified");
            }else{
                throw new AlreadyExistsException("A patient with the id card: " + modifiedPatient.getIdCard() +" already exists");
            }
        return modifiedPatient;
    }

    public Patient deletePatient(Long id) throws ResourceNotFoundException {
           Patient p = patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No patient was found to delete with the id:" + id));
            patientRepository.delete(p);
            logger.info("The patient " + p.getName() + " " + p.getSurname() + " has been deleted");
        return p;
    }

    public Patient findById(Long id) throws ResourceNotFoundException {
        return patientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Patient with the id: " + id + "not found"));
    }

    public Patient findByIdCard(Long idCard) throws AlreadyExistsException {
        Patient patient = patientRepository.findByIdCard(idCard);
        DischargedPatientMongo dischargedPatientMongo = patientRepositoryMongo.findByidCard(idCard);
        if(patient == null && dischargedPatientMongo == null){
            throw new AlreadyExistsException("Patient with idCard: " + idCard + " dosent exist");
        }
        if(patient != null){
            return patient;
        }
        return new Patient(dischargedPatientMongo.getName(), dischargedPatientMongo.getSurname(), dischargedPatientMongo.getIdCard(), dischargedPatientMongo.getDischargeDate());
    }

    public Patient findByEmail(String email) throws ResourceNotFoundException {
        Patient patient = patientRepository.findByEmail(email);
        if(patient == null){
            throw new ResourceNotFoundException("User not found");
        }
        return patient;
    }


    public Appointment addAppointment(AppointmentWrapper appointment) throws ResourceNotFoundException {
            Dentist dentist = dentistService.findById(appointment.getDentist_id());
            Patient patient = this.findById(appointment.getPatient_id());
             Appointment realAppointment = new Appointment();
                realAppointment.setDentist(dentist);
                realAppointment.setPatient(patient);
                realAppointment.setAppointmentDate(appointment.getAppointmentDate());
                realAppointment.setReason(appointment.getReason());
                patient.getAppointments().add(realAppointment);
                dentist.getAppointments().add(realAppointment);
                appointmentRepository.save(realAppointment);
        return realAppointment;
    }

    public List<AppointmentDTO> listAppointments(Long idCard) throws ResourceNotFoundException{
        List<AppointmentDTO> appointmentDTOList = null;;
            Patient foundPatient = patientRepository.findByIdCard(idCard);
            if(foundPatient == null){
               DischargedPatientMongo patientMongo = patientRepositoryMongo.findByidCard(idCard);
               String msg = "Patient with id: " + idCard + " dosent exist. ";
               if(patientMongo != null){
                   msg += " A discharged patient has been found: " + patientMongo.getName() + " " + patientMongo.getSurname() + " discharged on: " + patientMongo.getDischargeDate();
               }
                throw new ResourceNotFoundException(msg);
            }
        Set<Appointment> appointmentHashSet = foundPatient.getAppointments();
                appointmentDTOList = new ArrayList<>();
                for (Appointment a : appointmentHashSet) {
                    appointmentDTOList.add(objectMapper.convertValue(a, AppointmentDTO.class));
                }
        return appointmentDTOList;
    }



    public String deleteAppointment(Long id) throws ResourceNotFoundException {
        Appointment appointment= appointmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No appointment found to be deleted"));
        appointmentRepository.delete(appointment);
        return "Appointment deleted successfully";
    }


}

