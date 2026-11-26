package com.abes.doctor_appointment_system.Repository;

import com.abes.doctor_appointment_system.Entity.Doctors;
import com.abes.doctor_appointment_system.Entity.DoctorsSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DoctorsSlotRepository
        extends JpaRepository<DoctorsSlot, Long> {

    // Find all slots of a doctor on a particular date
    List<DoctorsSlot> findByDoctors_IdAndDate(
            Long doctorId, LocalDate date);


}
