package com.DentalClinicX.DentalClinicManagement.persistance.repository;
import com.DentalClinicX.DentalClinicManagement.persistance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
