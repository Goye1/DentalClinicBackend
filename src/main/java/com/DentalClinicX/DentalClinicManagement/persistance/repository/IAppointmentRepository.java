package com.DentalClinicX.DentalClinicManagement.persistance.repository;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface IAppointmentRepository  extends JpaRepository<Appointment,Long> {
    List<Appointment> findByAppointmentDateBefore(LocalDateTime date);
    Set<Appointment> findAllByPatientIdCard(Long idCard);


}
