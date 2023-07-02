package com.DentalClinicX.DentalClinicManagement.persistance.repositoryMongo;

import com.DentalClinicX.DentalClinicManagement.persistance.entityMongo.DischargedPatientMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPatientRepositoryMongo extends MongoRepository<DischargedPatientMongo,String> {

    boolean existsByidCard(Integer idCard);
    DischargedPatientMongo findByidCard(Long idCard);
}
