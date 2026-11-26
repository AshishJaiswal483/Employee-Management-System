package com.abes.doctor_appointment_system.Service;
import com.abes.doctor_appointment_system.DTO.UserPatientDtoRegistration;
import com.abes.doctor_appointment_system.Entity.Patient;
import com.abes.doctor_appointment_system.Entity.User;
import com.abes.doctor_appointment_system.Repository.PatientsRepositoty;
import com.abes.doctor_appointment_system.Repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {
    private PatientsRepositoty patientsRepositoty;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

   public PatientService(PatientsRepositoty patientsRepositoty, UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.patientsRepositoty=patientsRepositoty;
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    //  user registration and patient profile
    @Transactional
    public String Profile(UserPatientDtoRegistration dto){
        String email = dto.getEmail();
        Optional<User> check=userRepository.findByEmail(email);

        if (!check.isEmpty()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setRole("PATIENT");
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());

        User savedUser =userRepository.save(user);

        Patient patient = new Patient();
        patient.setAge(dto.getAge());
        patient.setGender(dto.getGender());
        patient.setPhone(dto.getPhone());
        patient.setPatientName(dto.getPatientName());
        patient.setUser(savedUser);

        patientsRepositoty.save(patient);

        return "Patient registered successfully";
    }
}
