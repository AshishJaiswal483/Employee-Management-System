package com.abes.doctor_appointment_system.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserDoctorDtoRegistration {

    @NotBlank(message = "Doctor name cannot be blank")
    private String doctorName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotNull(message = "Speciality cannot be null")
    private Long specialityId;

    @NotBlank(message = "Appointment mode cannot be blank")
    private String appointmentMode;

    public @NotBlank(message = "Doctor name cannot be blank") String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(@NotBlank(message = "Doctor name cannot be blank") String doctorName) {
        this.doctorName = doctorName;
    }

    public @NotBlank(message = "Email cannot be blank") @Email(message = "Enter a valid email") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email cannot be blank") @Email(message = "Enter a valid email") String email) {
        this.email = email;
    }

    public @NotBlank(message = "Password cannot be blank") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password cannot be blank") String password) {
        this.password = password;
    }

    public @NotNull(message = "Speciality cannot be null") Long getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(@NotNull(message = "Speciality cannot be null") Long specialityId) {
        this.specialityId = specialityId;
    }

    public @NotBlank(message = "Appointment mode cannot be blank") String getAppointmentMode() {
        return appointmentMode;
    }

    public void setAppointmentMode(@NotBlank(message = "Appointment mode cannot be blank") String appointmentMode) {
        this.appointmentMode = appointmentMode;
    }
}
