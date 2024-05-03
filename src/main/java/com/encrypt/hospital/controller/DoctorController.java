package com.encrypt.hospital.controller;

import com.encrypt.hospital.model.Doctor;
import com.encrypt.hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")  // 允许前端端口 3000 的跨域请求
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.findAllDoctors();
    }

    @PostMapping
    public Doctor addDoctor(@RequestBody Doctor doctor) {
        doctor.setUserId(1);  // 设置一个固定的 UserID, 适用于测试
        return doctorService.saveDoctor(doctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable int id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable int id, @RequestBody Doctor doctorDetails) {
        Doctor doctor = doctorService.findDoctorById(id);
        if (doctor == null) {
            return ResponseEntity.notFound().build();
        }
        doctor.setDocName(doctorDetails.getDocName());
        doctor.setDepartment(doctorDetails.getDepartment());
        doctor.setTitle(doctorDetails.getTitle());
        doctor.setEmail(doctorDetails.getEmail());
        doctor.setPhoneNumber(doctorDetails.getPhoneNumber());
        doctor.setProfile(doctorDetails.getProfile());

        Doctor updatedDoctor = doctorService.saveDoctor(doctor);
        return ResponseEntity.ok(updatedDoctor);
    }

}
