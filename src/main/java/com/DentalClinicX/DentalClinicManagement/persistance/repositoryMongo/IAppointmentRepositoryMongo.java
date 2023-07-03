package com.DentalClinicX.DentalClinicManagement.persistance.repositoryMongo;
import com.DentalClinicX.DentalClinicManagement.persistance.entityMongo.PastAppointmentMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IAppointmentRepositoryMongo extends MongoRepository<PastAppointmentMongo,String> {
    List<PastAppointmentMongo> findByPatient_idCard(Long idCard);
    List<PastAppointmentMongo> findByDentist_licenseNumber(Integer licenseNumber);
}
