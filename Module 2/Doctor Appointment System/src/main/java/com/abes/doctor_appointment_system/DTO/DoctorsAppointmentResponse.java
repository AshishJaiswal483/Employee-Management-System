package com.abes.doctor_appointment_system.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class DoctorsAppointmentResponse {
    private Long appointmentId;
    private String patientName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;

    public DoctorsAppointmentResponse(
            Long appointmentId,
            String patientName,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime,
            String status) {

        this.appointmentId = appointmentId;
        this.patientName = patientName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
