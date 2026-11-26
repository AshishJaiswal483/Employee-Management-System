package com.abes.doctor_appointment_system.Service;

import com.abes.doctor_appointment_system.Entity.Speciality;
import com.abes.doctor_appointment_system.Repository.SpecialityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialityService {
    private final SpecialityRepository specialityRepository;

   public SpecialityService(SpecialityRepository specialityRepository){
       this.specialityRepository=specialityRepository;
   }
    // 1. Add Speciality
    public Speciality addSpeciality(Speciality speciality) {

        if (specialityRepository.existsBySpecialityName(
                speciality.getSpecialityName())) {

            throw new RuntimeException("Speciality already exists");
        }

        return specialityRepository.save(speciality);
    }

    // 2. Get All Specialities
    public List<Speciality> getAllSpecialities() {

        List<Speciality>list=specialityRepository.findAll();
        if(list.isEmpty()){
            throw new RuntimeException("Speciality is empty");
        }
        return list;
    }

    // 3. Get Speciality By ID
    public Speciality getSpecialityById(Long id) {

        return specialityRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Speciality not found with ID: " + id));
    }

    // 4. Update Speciality
    public Speciality updateSpeciality(
            Long id, Speciality updatedSpeciality) {

        Speciality existingSpeciality =
                specialityRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Speciality not found with ID: " + id));

        if(specialityRepository.existsBySpecialityName(updatedSpeciality.getSpecialityName())==true){

            throw new RuntimeException("Speciality already exits");
        }
        existingSpeciality.setSpecialityName(updatedSpeciality.getSpecialityName());
        specialityRepository.save(existingSpeciality);
        return existingSpeciality;
    }
}
