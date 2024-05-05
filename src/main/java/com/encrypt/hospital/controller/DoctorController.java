package com.encrypt.hospital.controller;

import com.encrypt.hospital.model.Doctor;
import com.encrypt.hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/details/{userId}")
    public ResponseEntity<Doctor> getDoctorDetails(@PathVariable int userId) {
        return ResponseEntity.ok(doctorService.getDoctorDetails(userId));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateDoctorDetails(@RequestBody Map<String, Object> payload) {
        try {
            int userId = (int) payload.get("userId");
            Map<String, String> values = (Map<String, String>) payload.get("values");
            Doctor doctor = new Doctor();
            doctor.setUserId(userId);
            doctor.setDocName(values.get("docName"));
            doctor.setDepartment(values.get("department"));
            doctor.setTitle(values.get("title"));
            doctor.setEmail(values.get("email"));
            doctor.setPhoneNumber(values.get("phoneNumber"));
            doctor.setProfile(values.get("profile"));
            doctorService.updateDoctorDetails(doctor);
            return ResponseEntity.ok("个人信息修改成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新失败: " + e.getMessage());
        }
    }
}