
package com.abes.doctor_appointment_system.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "appointmentsTable")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many appointments can belong to one patient
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    // Multiple appointment records can refer to one slot
    @ManyToOne
    @JoinColumn(name = "slot_id")
    @JsonIgnore
    private DoctorsSlot slot;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public DoctorsSlot getSlot() {
        return slot;
    }

    public void setSlot(DoctorsSlot slot) {
        this.slot = slot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}


