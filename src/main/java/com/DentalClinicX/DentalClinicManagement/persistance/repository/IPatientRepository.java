package com.DentalClinicX.DentalClinicManagement.persistance.repository;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IPatientRepository extends JpaRepository<Patient,Long> {

    boolean existsByidCard(Integer idCard);
    List<Patient> findByDischargeDateBefore(LocalDate date);
    Patient findByIdCard(Long id);
}
