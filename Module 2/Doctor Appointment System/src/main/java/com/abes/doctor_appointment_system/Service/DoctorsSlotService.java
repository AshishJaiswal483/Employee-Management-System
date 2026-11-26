package com.abes.doctor_appointment_system.Service;

import com.abes.doctor_appointment_system.Entity.Doctors;
import com.abes.doctor_appointment_system.Entity.DoctorsSlot;
import com.abes.doctor_appointment_system.Repository.DoctorsRepository;
import com.abes.doctor_appointment_system.Repository.DoctorsSlotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorsSlotService {
    private final DoctorsSlotRepository doctorsSlotRepository;
    private final DoctorsRepository doctorsRepository;

    public DoctorsSlotService(
            DoctorsSlotRepository doctorsSlotRepository,
            DoctorsRepository doctorsRepository) {

        this.doctorsSlotRepository = doctorsSlotRepository;
        this.doctorsRepository = doctorsRepository;
    }

    @Transactional
    //add slot and assign to doctor
    public List<DoctorsSlot> addSlots(
            Long doctorId,
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime) {

        // 1. Find doctor
        Doctors doctor = doctorsRepository.findById(doctorId)
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found"));

        // 2. Validate date
        if (date.isBefore(LocalDate.now())) {
            throw new RuntimeException(
                    "Cannot create slots for past dates");
        }

        // 3. Validate time range
        if (!endTime.isAfter(startTime)) {
            throw new RuntimeException(
                    "End time must be after start time");
        }

        // 4. Validate duration
        long totalMinutes =
                Duration.between(startTime, endTime).toMinutes();

        if (totalMinutes % 30 != 0) {
            throw new RuntimeException(
                    "Time range must be divisible by 30 minutes");
        }

        // 5. Find existing slots for this doctor and date
        List<DoctorsSlot> existingSlots =
                doctorsSlotRepository.findByDoctors_IdAndDate(
                        doctorId, date);

        List<DoctorsSlot> newSlots = new ArrayList<>();

        // 6. Generate 30-minute slots
        LocalTime currentStart = startTime;

        while (currentStart.isBefore(endTime)) {

            LocalTime currentEnd = currentStart.plusMinutes(30);

            // 7. Check overlapping slots
            for (DoctorsSlot existingSlot : existingSlots) {

                boolean overlap =
                        currentStart.isBefore(existingSlot.getEndTime())
                                && currentEnd.isAfter(existingSlot.getStartTime());

                if (overlap) {
                    throw new RuntimeException(
                            "Slot overlaps with existing availability");
                }
            }

            // 8. Create slot object
            DoctorsSlot slot = new DoctorsSlot();

            slot.setDate(date);
            slot.setStartTime(currentStart);
            slot.setEndTime(currentEnd);

            newSlots.add(slot);

            currentStart = currentEnd;
        }

        // 9. Connect each slot with the doctor
        for (DoctorsSlot slot : newSlots) {
            slot.setDoctors(doctor);
            doctor.getSlots().add(slot);
        }

        // 10. Save all slots in database
        return doctorsSlotRepository.saveAll(newSlots);
    }



    //get all slot of doctor
    public List<DoctorsSlot> getDocotorSlots(Long id) {

        Doctors doctor = doctorsRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Doctor id does not exist"));

        List<DoctorsSlot> slots = doctor.getSlots();

        if (slots.isEmpty()) {
            throw new RuntimeException("No slots available");
        }

        return slots;
    }

    public List<DoctorsSlot> getDoctorSlotAvalability(Long id, LocalDate date) {

        // 1. Check whether doctor exists
        if (!doctorsRepository.existsById(id)) {
            throw new RuntimeException("Doctor does not exist");
        }

        // 2. Check whether requested date is in the past
        LocalDate today = LocalDate.now();

        if (date.isBefore(today)) {
            throw new RuntimeException("Cannot search past dates");
        }

        // 3. Fetch slots for the selected doctor and date
        List<DoctorsSlot> slots =
                doctorsSlotRepository.findByDoctors_IdAndDate(id, date);

        // 4. Get current time
        LocalTime currentTime = LocalTime.now();

        // 5. Filter available future slots
        List<DoctorsSlot> availableSlots = slots.stream()
                .filter(slot -> {

                    // Check whether slot has any BOOKED appointment
                    boolean notBooked = slot.getAppointments().stream()
                            .noneMatch(appointment ->
                                    "BOOKED".equals(appointment.getStatus()));

                    // Check whether slot is in the future
                    boolean future = date.isAfter(today)
                            || slot.getStartTime().isAfter(currentTime);

                    return notBooked && future;
                })
                .toList();

        // 6. Return available slots
        return availableSlots;
    }
}

