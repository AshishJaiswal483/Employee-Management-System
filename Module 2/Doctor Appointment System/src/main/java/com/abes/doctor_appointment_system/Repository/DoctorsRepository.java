package com.abes.doctor_appointment_system.Repository;

import com.abes.doctor_appointment_system.Entity.Doctors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorsRepository extends JpaRepository<Doctors,Long> {
    List<Doctors>findBySpeciality_Id(Long id);
    boolean existsByDoctorNameAndAppointmentMode(
            String doctorName, String appointmentMode);

    List<Doctors> findByAppointmentMode(String appointmentMode);
    Optional<Doctors> findByUser_Email(String email);

}
