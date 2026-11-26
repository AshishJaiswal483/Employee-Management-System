package com.abes.doctor_appointment_system.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserPatientDtoRegistration {
    @NotBlank(message = "cannot be blank")
    private String patientName;


    @NotNull(message = "cannot be null")
    @Min(value = 1)
    private Integer age;

    @NotBlank(message = "cannot be blank")
    private String gender;

    @NotBlank(message = "cannot be blank")
    private String phone;

    @NotBlank(message = "cannot be blank")
    @Email(message = "enter a valid email")
    private String email;

    @NotBlank(message = "cannot be blank")
    private String password;

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
