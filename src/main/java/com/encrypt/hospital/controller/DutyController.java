package com.encrypt.hospital.controller;

import com.encrypt.hospital.repository.AppointmentCountDto;
import com.encrypt.hospital.service.DutyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/duty")
public class DutyController {
    @Autowired
    private DutyService dutyService;

    @GetMapping("/duties/{docID}")
    public ResponseEntity<?> getDoctorDuties(@PathVariable int docID) {
        try {
            List<AppointmentCountDto> duties = dutyService.getAppointmentCountsByDoctor(docID);
            return ResponseEntity.ok(duties);
        } catch (Exception e) {
            e.printStackTrace(); // Log the stack trace for debugging
            return ResponseEntity.status(500).body("Error processing request: " + e.getMessage());
        }
    }
}
