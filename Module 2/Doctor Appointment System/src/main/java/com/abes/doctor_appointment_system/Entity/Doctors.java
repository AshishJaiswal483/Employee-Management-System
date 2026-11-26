
package com.abes.doctor_appointment_system.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctorTable")
public class Doctors {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String doctorName;

    private String appointmentMode;

    // Many doctors can belong to one speciality
    @ManyToOne
    @JoinColumn(name = "specialityId")
    private Speciality speciality;

    // One doctor can have multiple slots
    @OneToMany(mappedBy = "doctors")
    @JsonIgnore
    private List<DoctorsSlot> slots = new ArrayList<>();

    // One doctor is connected to one user
    @OneToOne
    @JoinColumn(name = "userId", unique = true)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getAppointmentMode() {
        return appointmentMode;
    }

    public void setAppointmentMode(String appointmentMode) {
        this.appointmentMode = appointmentMode;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public List<DoctorsSlot> getSlots() {
        return slots;
    }

    public void setSlots(List<DoctorsSlot> slots) {
        this.slots = slots;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

