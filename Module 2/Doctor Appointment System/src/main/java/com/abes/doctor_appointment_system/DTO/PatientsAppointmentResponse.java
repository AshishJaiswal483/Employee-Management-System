package com.abes.doctor_appointment_system.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class PatientsAppointmentResponse {
    private Long appointmentId;
    private String doctorName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;

    public PatientsAppointmentResponse(Long appointmentId, String doctorName,
                                       LocalDate date, LocalTime startTime,
                                       LocalTime endTime, String status) {
        this.appointmentId = appointmentId;
        this.doctorName = doctorName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getStatus() {
        return status;
    }
}
