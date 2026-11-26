package com.abes.doctor_appointment_system.Repository;

import com.abes.doctor_appointment_system.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {

    // Check whether a slot is already booked
    boolean existsBySlot_IdAndStatus(Long slotId, String status);

    // Find all appointments of a particular patient
    List<Appointment> findByPatient_Id(Long patientId);
    List<Appointment>findBySlot_Doctors_Id(Long doctorId);

}