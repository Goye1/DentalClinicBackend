package com.DentalClinicX.DentalClinicManagement.service;


import com.DentalClinicX.DentalClinicManagement.persistance.entity.Appointment;
import com.DentalClinicX.DentalClinicManagement.persistance.entityMongo.AppointmentMongo;
import com.DentalClinicX.DentalClinicManagement.persistance.entityMongo.DentistMongo;
import com.DentalClinicX.DentalClinicManagement.persistance.entityMongo.PatientMongo;
import com.DentalClinicX.DentalClinicManagement.persistance.repository.IAppointmentRepository;
import com.DentalClinicX.DentalClinicManagement.persistance.repositoryMongo.IAppointmentRepositoryMongo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentServiceMongo {
    private final IAppointmentRepository appointmentRepository;
    private final IAppointmentRepositoryMongo appointmentMongoRepository;

    @Autowired
    private Logger logger;
    @Autowired
    public AppointmentServiceMongo(IAppointmentRepository appointmentRepository, IAppointmentRepositoryMongo appointmentMongoRepository) {
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
                PatientMongo patientMongo = new PatientMongo(appointment.getPatient().getName(), appointment.getDentist().getSurname(), appointment.getPatient().getDischargeDate());
                AppointmentMongo appointmentMongo = new AppointmentMongo(appointment.getAppointmentDate(), dentistMongo, patientMongo);
                appointmentMongoRepository.save(appointmentMongo);
                appointmentRepository.delete(appointment);
            }
        }
    }
}
