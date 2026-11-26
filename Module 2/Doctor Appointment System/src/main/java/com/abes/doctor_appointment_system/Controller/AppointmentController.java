package com.abes.doctor_appointment_system.Controller;

import com.abes.doctor_appointment_system.DTO.DoctorsAppointmentResponse;
import com.abes.doctor_appointment_system.DTO.PatientsAppointmentResponse;
import com.abes.doctor_appointment_system.Entity.Appointment;
import com.abes.doctor_appointment_system.Service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/appointment/api")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // 1. Book Appointment
    @PostMapping("/book/{slotId}")
    public ResponseEntity<String> bookAppointment(
            @PathVariable Long slotId,
            Principal principal) {

        Appointment appointment =
                appointmentService.bookAppointment(
                        slotId,
                        principal.getName()
                );
        return ResponseEntity.ok(
                "Appointment booked successfully. Appointment ID: "
                        + appointment.getId()
        );
    }

    // 2. Cancel Appointment
    @PutMapping("/cancel/{appointmentId}")
    public ResponseEntity<String> cancelAppointment(
            @PathVariable Long appointmentId,
            Principal principal) {

        appointmentService.cancelAppointment(
                appointmentId,
                principal.getName()
        );

        return ResponseEntity.ok(
                "Appointment cancelled successfully"
        );
    }

    // 3. Get My Appointments
    @GetMapping("/myAppointments")
    public ResponseEntity<List<PatientsAppointmentResponse>> getMyAppointments(
            Principal principal) {

        List<PatientsAppointmentResponse> appointments =
                appointmentService.getMyAppointments(
                        principal.getName()
                );

        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/doctorAppointments")
    public ResponseEntity<List<DoctorsAppointmentResponse>> getDoctorAppointments(
            Principal principal) {

        List<DoctorsAppointmentResponse> appointments =
                appointmentService.getDoctorAppointments(principal.getName());

        return ResponseEntity.ok(appointments);
    }
}
