package com.encrypt.hospital.controller;

import com.encrypt.hospital.model.Doctor;
import com.encrypt.hospital.model.Patient;
import com.encrypt.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/details/{userID}")
    public ResponseEntity<Patient> getPatientDetails(@PathVariable int userID) {
        return ResponseEntity.ok(patientService.getPatientDetails(userID));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updatePatientDetails(@RequestBody Map<String, Object> payload) {
        try {
            int userID = (int) payload.get("userId");
            Map<String, String> values = (Map<String, String>) payload.get("values");
            Patient patient = new Patient();
            patient.setUserID(userID);
            patient.setPatientName(values.get("patientName"));
            patient.setSex(values.get("sex"));

            patient.setPhoneNumber(values.get("phoneNumber"));
            patient.setNationalID(values.get("nationalID"));
            patient.setAge(values.get("age"));
            patientService.updatePatientDetails(patient);
            return ResponseEntity.ok("个人信息修改成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新失败: " + e.getMessage());
        }
    }
}
