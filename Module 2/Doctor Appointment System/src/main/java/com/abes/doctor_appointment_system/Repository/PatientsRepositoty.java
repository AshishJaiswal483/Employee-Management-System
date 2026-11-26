package com.abes.doctor_appointment_system.Repository;

import com.abes.doctor_appointment_system.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientsRepositoty extends JpaRepository<Patient,Long> {

    Patient findByUser_Email(String email);
}
