package com.DentalClinicX.DentalClinicManagement.persistance.repositoryMongo;

import com.DentalClinicX.DentalClinicManagement.persistance.entityMongo.AppointmentMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IAppointmentRepositoryMongo extends MongoRepository<AppointmentMongo,String> {

}
