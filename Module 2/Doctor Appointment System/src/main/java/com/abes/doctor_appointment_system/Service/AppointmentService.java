package com.abes.doctor_appointment_system.Service;

import com.abes.doctor_appointment_system.DTO.DoctorsAppointmentResponse;
import com.abes.doctor_appointment_system.DTO.PatientsAppointmentResponse;
import com.abes.doctor_appointment_system.Entity.Appointment;
import com.abes.doctor_appointment_system.Entity.Doctors;
import com.abes.doctor_appointment_system.Entity.DoctorsSlot;
import com.abes.doctor_appointment_system.Entity.Patient;
import com.abes.doctor_appointment_system.Repository.AppointmentRepository;
import com.abes.doctor_appointment_system.Repository.DoctorsRepository;
import com.abes.doctor_appointment_system.Repository.DoctorsSlotRepository;
import com.abes.doctor_appointment_system.Repository.PatientsRepositoty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorsSlotRepository doctorsSlotRepository;
    private final PatientsRepositoty patientsRepositoty;
    private final DoctorsRepository doctorsRepository;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            DoctorsSlotRepository doctorsSlotRepository,
            PatientsRepositoty patientsRepositoty,DoctorsRepository doctorsRepository) {

        this.appointmentRepository = appointmentRepository;
        this.doctorsSlotRepository = doctorsSlotRepository;
        this.patientsRepositoty = patientsRepositoty;
        this.doctorsRepository = doctorsRepository;
    }

    @Transactional
    public Appointment bookAppointment(Long slotId, String email) {

        // 1. Find the selected slot
        DoctorsSlot slot = doctorsSlotRepository.findById(slotId)
                .orElseThrow(() ->
                        new RuntimeException("Slot does not exist"));

        // 2. Check whether the slot date/time is in the future
        LocalDate today = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        if (slot.getDate().isBefore(today) ||
                (slot.getDate().isEqual(today) &&
                        !slot.getStartTime().isAfter(currentTime))) {

            throw new RuntimeException("Cannot book a past slot");
        }

        // 3. Check whether the slot is already booked
        boolean alreadyBooked =
                appointmentRepository
                        .existsBySlot_IdAndStatus(slotId, "BOOKED");

        if (alreadyBooked) {
            throw new RuntimeException("Slot is already booked");
        }

        // 4. Find patient using logged-in user's email
        Patient patient = patientsRepositoty.findByUser_Email(email);

        if (patient == null) {
            throw new RuntimeException("Patient profile not found");
        }

        // 5. Create appointment
        Appointment appointment = new Appointment();

        patient.addAppointment(appointment);
        slot.addAppointment(appointment);
        appointment.setStatus("BOOKED");

        // 6. Save appointment
        return appointmentRepository.save(appointment);
    }

    @Transactional
    public void cancelAppointment(Long appointmentId, String email) {

        Appointment appointment =
                appointmentRepository.findById(appointmentId)
                        .orElseThrow(() ->
                                new RuntimeException("Appointment not found"));

        // Check that this appointment belongs to logged-in patient
        if (!appointment.getPatient().getUser().getEmail().equals(email)) {
            throw new RuntimeException("You cannot cancel this appointment");
        }

        if (!appointment.getStatus().equals("BOOKED")) {
            throw new RuntimeException("Appointment is not active");
        }

        appointment.setStatus("CANCELLED");

        appointmentRepository.save(appointment);
    }


    public List<PatientsAppointmentResponse> getMyAppointments(String email) {

        // 1. Find logged-in patient
        Patient patient = patientsRepositoty.findByUser_Email(email);
        if (patient == null) {
            throw new RuntimeException("Patient profile not found");
        }
        // 2. Find all appointments of this patient
        List<Appointment> appointments =
                appointmentRepository.findByPatient_Id(patient.getId());

        // 3. Convert Appointment into AppointmentResponse
        return appointments.stream()
                .map(appointment -> new PatientsAppointmentResponse(
                        appointment.getId(),
                        appointment.getSlot().getDoctors().getDoctorName(),
                        appointment.getSlot().getDate(),
                        appointment.getSlot().getStartTime(),
                        appointment.getSlot().getEndTime(),
                        appointment.getStatus()
                ))
                .toList();
    }

    public List<DoctorsAppointmentResponse> getDoctorAppointments(String email) {

        // 1. Find doctor using logged-in emai
        Optional<Doctors> doctor = doctorsRepository.findByUser_Email(email);
        if (doctor.isEmpty()) {
            throw new RuntimeException("Doctor profile not found");
        }

        // 2. Fetch appointments using doctor ID
        List<Appointment> appointments =
                appointmentRepository.findBySlot_Doctors_Id(doctor.get().getId());

        // 3. Convert Appointment entity into DTO
        return appointments.stream()
                .map(appointment -> new DoctorsAppointmentResponse(
                        appointment.getId(),
                        appointment.getPatient().getPatientName(),
                        appointment.getSlot().getDate(),
                        appointment.getSlot().getStartTime(),
                        appointment.getSlot().getEndTime(),
                        appointment.getStatus()
                ))
                .toList();
    }

}
