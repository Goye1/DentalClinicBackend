package com.DentalClinicX.DentalClinicManagement.service;

import com.DentalClinicX.DentalClinicManagement.persistance.entity.Appointment;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Patient;
import com.DentalClinicX.DentalClinicManagement.persistance.entityMongo.AppointmentMongo;
import com.DentalClinicX.DentalClinicManagement.persistance.entityMongo.DentistMongo;
import com.DentalClinicX.DentalClinicManagement.persistance.entityMongo.PatientMongo;
import com.DentalClinicX.DentalClinicManagement.persistance.repository.IPatientRepository;
import com.DentalClinicX.DentalClinicManagement.persistance.repositoryMongo.IPatientRepositoryMongo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientServiceMongo {

    private final IPatientRepository patientRepository;
    private final IPatientRepositoryMongo patientRepositoryMongo;

    @Autowired
    private Logger logger;

    public PatientServiceMongo(IPatientRepository patientRepository, IPatientRepositoryMongo patientRepositoryMongo) {
        this.patientRepository = patientRepository;
        this.patientRepositoryMongo = patientRepositoryMongo;
    }

    public void processPatients() {
        LocalDate currentDate = LocalDate.now();
        logger.info("processing patients. " + currentDate);
        List<Patient> patients = patientRepository.findByDischargeDateBefore(currentDate);
        if (!patients.isEmpty()) {
            for (Patient patient : patients) {
                PatientMongo patientMongo = new PatientMongo(patient.getName(),patient.getSurname(), patient.getDischargeDate());
                patientRepositoryMongo.save(patientMongo);
                patientRepository.delete(patient);
            }
        }
    }
}
