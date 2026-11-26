
package com.abes.doctor_appointment_system.Service;

import com.abes.doctor_appointment_system.Configurations.PasswordConfig;
import com.abes.doctor_appointment_system.DTO.UserDoctorDtoRegistration;
import com.abes.doctor_appointment_system.Entity.Doctors;
import com.abes.doctor_appointment_system.Entity.Speciality;
import com.abes.doctor_appointment_system.Entity.User;
import com.abes.doctor_appointment_system.Repository.DoctorsRepository;
import com.abes.doctor_appointment_system.Repository.SpecialityRepository;

import com.abes.doctor_appointment_system.Repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorsRepository doctorsRepository;
    private final SpecialityRepository specialityRepository;
    private  final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DoctorService(DoctorsRepository doctorsRepository,
                         SpecialityRepository specialityRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.doctorsRepository = doctorsRepository;
        this.specialityRepository = specialityRepository;
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }



    // 1. Add Doctor
    @Transactional
    public Doctors addDoctor(UserDoctorDtoRegistration dto) {
        // Check duplicate doctor
        if (doctorsRepository.existsByDoctorNameAndAppointmentMode(
                dto.getDoctorName(), dto.getAppointmentMode())) {

            throw new RuntimeException(
                    "Doctor already exists with this appointment mode");
        }

        // Check duplicate email
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // Find speciality
        Speciality speciality = specialityRepository.findById(dto.getSpecialityId())
                .orElseThrow(() ->
                        new RuntimeException("Speciality not found"));

        // Create User
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("DOCTOR");

        User savedUser = userRepository.save(user);

        // Create Doctor
        Doctors doctor = new Doctors();
        doctor.setDoctorName(dto.getDoctorName());
        doctor.setAppointmentMode(dto.getAppointmentMode());
        doctor.setUser(savedUser);
        savedUser.setDoctor(doctor);

        // Connect speciality and doctor
        speciality.addDoctor(doctor);

        return doctorsRepository.save(doctor);
    }


//    // 2. Get All Doctors
//    public List<Doctors> getAllDoctors() {
//
//        List<Doctors> list = doctorsRepository.findAll();
//
//        if (list.isEmpty()) {
//            throw new RuntimeException("Doctor list is empty");
//        }
//
//        return list;
//    }


    // 3. Get Doctor By ID
    public Doctors getDoctorById(Long id) {

        return doctorsRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Doctor not found with ID: " + id));
    }


    // 4. Update Doctor (PUT)
    public Doctors updateDoctor(Long id, Doctors updatedDoctor) {

        Doctors existingDoctor = doctorsRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Doctor not found with ID: " + id));

        existingDoctor.setDoctorName(updatedDoctor.getDoctorName());

        existingDoctor.setAppointmentMode(
                updatedDoctor.getAppointmentMode());

        Long specialityId = updatedDoctor.getSpeciality().getId();

        Speciality newSpeciality = specialityRepository.findById(specialityId)
                .orElseThrow(() ->
                        new RuntimeException("Speciality not found"));

        Speciality oldSpeciality = existingDoctor.getSpeciality();

        if (oldSpeciality != null) {
            oldSpeciality.getDoctors().remove(existingDoctor);
        }

        newSpeciality.addDoctor(existingDoctor);

        return doctorsRepository.save(existingDoctor);
    }


    // 5. Get Doctors By Speciality
    public List<Doctors> getDoctorsBySpeciality(Long specialityId) {

        Speciality speciality = specialityRepository.findById(specialityId)
                .orElseThrow(() ->
                        new RuntimeException("Speciality not found"));

        List<Doctors> list = speciality.getDoctors();

        if (list.isEmpty()) {
            throw new RuntimeException(
                    "No doctors found for this speciality");
        }

        return list;

    }


//    // 6. Get Doctors By Appointment Mode
//    public List<Doctors> getDoctorsByMode(String mode) {
//
//        List<Doctors> list =
//                doctorsRepository.findByAppointmentMode(mode);
//
//        if (list.isEmpty()) {
//            throw new RuntimeException(
//                    "No doctors found for this appointment mode");
//        }
//
//        return list;
//  }
//}

}
