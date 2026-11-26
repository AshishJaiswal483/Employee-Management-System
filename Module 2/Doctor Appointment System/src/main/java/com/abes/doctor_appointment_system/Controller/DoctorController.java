
package com.abes.doctor_appointment_system.Controller;

import com.abes.doctor_appointment_system.DTO.UserDoctorDtoRegistration;
import com.abes.doctor_appointment_system.Entity.Doctors;
import com.abes.doctor_appointment_system.Service.DoctorService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // 1. Add Doctor
    @PostMapping("/add")
    public ResponseEntity<String>addDoctor(@RequestBody @Valid UserDoctorDtoRegistration dto) {
        Doctors doctor = doctorService.addDoctor(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body("Doctor registered successfully");
    }

//    // 2. Get All Doctors
//    @GetMapping("/all")
//    public List<Doctors> getAllDoctors() {
//        return doctorService.getAllDoctors();
//    }

    // 3. Get Doctor By ID
    @GetMapping("/{id}")
    public Doctors getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    // 4. Update Doctor
    @PutMapping("/update/{id}")
    public Doctors updateDoctor(
            @PathVariable Long id,
            @RequestBody Doctors doctor) {

        return doctorService.updateDoctor(id, doctor);
    }

    // 5. Get Doctors By Speciality
    @GetMapping("/speciality/{id}")
    public List<Doctors> getDoctorsBySpeciality(
            @PathVariable Long id) {

        return doctorService.getDoctorsBySpeciality(id);
    }

//    // 6. Get Doctors By Appointment Mode
//    @GetMapping("/mode/{mode}")
//    public List<Doctors> getDoctorsByMode(@PathVariable String mode) {
//        return doctorService.getDoctorsByMode(mode);
//    }
}

