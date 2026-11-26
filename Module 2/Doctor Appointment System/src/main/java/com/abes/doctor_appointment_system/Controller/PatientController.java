package com.abes.doctor_appointment_system.Controller;

import com.abes.doctor_appointment_system.DTO.UserPatientDtoRegistration;
import com.abes.doctor_appointment_system.Service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient/api")
public class PatientController {
    private PatientService patientService;

    public PatientController(PatientService patientService){
        this.patientService=patientService;
    }

    @PostMapping("/patientRegistration")
    public ResponseEntity<String>patientRegistration(@RequestBody @Valid UserPatientDtoRegistration dto){
        String mssg =patientService.Profile(dto);
        return ResponseEntity.status(HttpStatus.OK).body(mssg);
    }
}
