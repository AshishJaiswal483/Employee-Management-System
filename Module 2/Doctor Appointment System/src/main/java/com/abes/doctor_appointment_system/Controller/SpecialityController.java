package com.abes.doctor_appointment_system.Controller;

import com.abes.doctor_appointment_system.Entity.Speciality;
import com.abes.doctor_appointment_system.Service.SpecialityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/speciality/api")
public class SpecialityController {
    private final SpecialityService specialityService;

    public SpecialityController(SpecialityService specialityService) {
        this.specialityService = specialityService;
    }

    // 1. Add Speciality
    @PostMapping("/add")
    public Speciality addSpeciality(@RequestBody Speciality speciality) {

        return specialityService.addSpeciality(speciality);
    }

    // 2. Get All Specialities
    @GetMapping("getAll")
    public List<Speciality> getAllSpecialities() {

        return specialityService.getAllSpecialities();
    }

    // 3. Get Speciality By ID
    @GetMapping("/getById/{id}")
    public Speciality getSpecialityById(@PathVariable Long id) {

        return specialityService.getSpecialityById(id);
    }

    // 4. Update Speciality
    @PutMapping("updateById/{id}")
    public Speciality updateSpeciality(
            @PathVariable Long id,
            @RequestBody Speciality speciality) {

        return specialityService.updateSpeciality(id, speciality);
    }
}
