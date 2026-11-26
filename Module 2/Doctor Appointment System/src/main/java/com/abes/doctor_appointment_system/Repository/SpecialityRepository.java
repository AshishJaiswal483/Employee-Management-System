package com.abes.doctor_appointment_system.Repository;

import com.abes.doctor_appointment_system.Entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality,Long> {

    boolean existsBySpecialityName(String specialityName);

}
