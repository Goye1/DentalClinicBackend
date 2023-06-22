package com.DentalClinicX.DentalClinicManagement.persistance.repositoryMongo;

import com.DentalClinicX.DentalClinicManagement.persistance.entityMongo.PatientMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPatientRepositoryMongo extends MongoRepository<PatientMongo,String> {
}
