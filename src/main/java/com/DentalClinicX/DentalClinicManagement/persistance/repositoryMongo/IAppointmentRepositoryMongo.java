package com.DentalClinicX.DentalClinicManagement.persistance.repositoryMongo;

import com.DentalClinicX.DentalClinicManagement.persistance.entityMongo.AppointmentMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAppointmentRepositoryMongo extends MongoRepository<AppointmentMongo,String> {
    List<AppointmentMongo> findByPatient_PatientID(Long patientID);
}
