package com.abes.doctor_appointment_system.Repository;

import com.abes.doctor_appointment_system.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {


    Optional<User> findByEmail(String username);
}
