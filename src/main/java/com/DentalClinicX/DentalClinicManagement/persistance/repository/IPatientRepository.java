package com.DentalClinicX.DentalClinicManagement.persistance.repository;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPatientRepository extends JpaRepository<Patient,Long> {

    boolean existsByidCard(Integer idCard);
    Patient findByName(String name);

}
