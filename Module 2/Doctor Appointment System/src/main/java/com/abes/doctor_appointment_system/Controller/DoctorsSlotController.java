package com.abes.doctor_appointment_system.Controller;

import com.abes.doctor_appointment_system.Entity.DoctorsSlot;
import com.abes.doctor_appointment_system.Service.DoctorsSlotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/Slot/api")

public class DoctorsSlotController {
    private DoctorsSlotService doctorsSlotService;

    public DoctorsSlotController(DoctorsSlotService doctorsSlotService){
        this.doctorsSlotService=doctorsSlotService;
    }

    @PostMapping("/addSlot")
    public ResponseEntity<List<DoctorsSlot>> addSlot(@RequestBody DoctorsSlot doctorsSlot){
        Long doctorId=doctorsSlot.getDoctors().getId();
        LocalDate date=doctorsSlot.getDate();
        LocalTime startTime=doctorsSlot.getStartTime();
        LocalTime endTime=doctorsSlot.getEndTime();
        List<DoctorsSlot>doctorsSlotList =doctorsSlotService.addSlots(doctorId,date,startTime,endTime);

        return ResponseEntity.status(HttpStatus.CREATED).body(doctorsSlotList);
    }

    @GetMapping("/getParticularDoctorSlot/{doctorId}")
    public ResponseEntity<List<DoctorsSlot>>getDoctorSlot(@PathVariable Long doctorId){
        List<DoctorsSlot>slots=doctorsSlotService.getDocotorSlots(doctorId);
        return ResponseEntity.status(HttpStatus.OK).body(slots);
    }
//
//    @GetMapping("/getDoctorsSlotByDateAndId/{id}")
//    public ResponseEntity<List<DoctorsSlot>>slots(@PathVariable Long id, @RequestParam LocalDate date){
//       List<DoctorsSlot>slots= doctorsSlotService.getDoctorSlot(id,date);
//       return ResponseEntity.status(HttpStatus.OK).body(slots);
//    }

    // Patient searches available slots using doctor ID and date
    @GetMapping("/getDoctorsSlotByDateAndId/{id}")
    public ResponseEntity<List<DoctorsSlot>> getDoctorSlots(
            @PathVariable Long id,
            @RequestParam LocalDate date) {

        List<DoctorsSlot> slots =
                doctorsSlotService.getDoctorSlotAvalability(id, date);

        return ResponseEntity.ok(slots);
    }
}


