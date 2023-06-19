package com.DentalClinicX.DentalClinicManagement.persistance.repositoryMongo;

import com.DentalClinicX.DentalClinicManagement.persistance.entityMongo.PatientMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IPatientRepositoryMongo extends MongoRepository<PatientMongo,String> {
}
