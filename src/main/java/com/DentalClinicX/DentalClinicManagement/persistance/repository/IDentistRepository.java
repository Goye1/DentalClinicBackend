package com.DentalClinicX.DentalClinicManagement.persistance.repository;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface IDentistRepository  extends JpaRepository<Dentist,Long> {
    boolean existsBylicenseNumber(Integer licenseNumber);

    Dentist findBylicenseNumber(Integer licenseNumber);
}
