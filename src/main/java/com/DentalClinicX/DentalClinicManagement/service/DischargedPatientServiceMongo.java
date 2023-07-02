package com.DentalClinicX.DentalClinicManagement.service;

import com.DentalClinicX.DentalClinicManagement.persistance.entity.Patient;
import com.DentalClinicX.DentalClinicManagement.persistance.entityMongo.DischargedPatientMongo;
import com.DentalClinicX.DentalClinicManagement.persistance.repository.IPatientRepository;
import com.DentalClinicX.DentalClinicManagement.persistance.repositoryMongo.IPatientRepositoryMongo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DischargedPatientServiceMongo {

    private final IPatientRepository patientRepository;
    private final IPatientRepositoryMongo patientRepositoryMongo;

    @Autowired
    private Logger logger;

    public DischargedPatientServiceMongo(IPatientRepository patientRepository, IPatientRepositoryMongo patientRepositoryMongo) {
        this.patientRepository = patientRepository;
        this.patientRepositoryMongo = patientRepositoryMongo;

    }

    public void processPatients() {
        LocalDate currentDate = LocalDate.now();
        logger.info("processing patients. " + currentDate);
        List<Patient> patients = patientRepository.findByDischargeDateBefore(currentDate);
        if (!patients.isEmpty()) {
            for (Patient patient : patients) {
                DischargedPatientMongo dischargedPatientMongo = new DischargedPatientMongo(patient.getName(),patient.getSurname(), patient.getIdCard(), patient.getDischargeDate(), patient.getId());
                patientRepositoryMongo.save(dischargedPatientMongo);
                patientRepository.delete(patient);
            }
        }
    }



}
