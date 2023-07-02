package com.DentalClinicX.DentalClinicManagement.service;


import com.DentalClinicX.DentalClinicManagement.exceptions.ResourceNotFoundException;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Appointment;
import com.DentalClinicX.DentalClinicManagement.persistance.entityMongo.PastAppointmentMongo;
import com.DentalClinicX.DentalClinicManagement.persistance.entityMongo.DentistMongo;
import com.DentalClinicX.DentalClinicManagement.persistance.entityMongo.DischargedPatientMongo;
import com.DentalClinicX.DentalClinicManagement.persistance.repository.IAppointmentRepository;
import com.DentalClinicX.DentalClinicManagement.persistance.repositoryMongo.IAppointmentRepositoryMongo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PastAppointmentServiceMongo {
    private final IAppointmentRepository appointmentRepository;
    private final IAppointmentRepositoryMongo appointmentMongoRepository;

    @Autowired
    private Logger logger;
    @Autowired
    public PastAppointmentServiceMongo(IAppointmentRepository appointmentRepository, IAppointmentRepositoryMongo appointmentMongoRepository) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMongoRepository = appointmentMongoRepository;
    }

    public void processAppointments() {
        LocalDateTime currentDate = LocalDateTime.now();
        logger.info("Processing appointments. " + currentDate);
        List<Appointment> appointments = appointmentRepository.findByAppointmentDateBefore(currentDate);
        if (!appointments.isEmpty()) {
            for (Appointment appointment : appointments) {
                DentistMongo dentistMongo = new DentistMongo(appointment.getDentist().getName(), appointment.getDentist().getSurname(), appointment.getDentist().getLicenseNumber());
                DischargedPatientMongo dischargedPatientMongo = new DischargedPatientMongo(appointment.getPatient().getName(), appointment.getDentist().getSurname(), appointment.getPatient().getIdCard(), appointment.getPatient().getDischargeDate(), appointment.getPatient().getId());
                PastAppointmentMongo pastAppointmentMongo = new PastAppointmentMongo(appointment.getAppointmentDate(), dentistMongo, dischargedPatientMongo, appointment.getReason());
                appointmentMongoRepository.save(pastAppointmentMongo);
                appointmentRepository.delete(appointment);
            }
        }
    }

    public List<PastAppointmentMongo> listPatientPastAppointments(Long idCard) throws ResourceNotFoundException {
        List<PastAppointmentMongo> pastAppointmentMongoList = appointmentMongoRepository.findByPatient_idCard(idCard);
        if(pastAppointmentMongoList == null){
            throw new ResourceNotFoundException("Patient with id card: " + idCard + " dosent exist");
        }
        return pastAppointmentMongoList;
    }

    public List<PastAppointmentMongo> listDentistPastAppointments(Integer licenseNumber) throws ResourceNotFoundException {
        List<PastAppointmentMongo> pastAppointmentMongoList = appointmentMongoRepository.findByDentist_licenseNumber(licenseNumber);
        if(pastAppointmentMongoList == null){
            throw new ResourceNotFoundException("Dentist with license number: " + licenseNumber + " donset exist");
        }
        return pastAppointmentMongoList;
    }

}
