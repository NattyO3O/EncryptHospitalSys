package com.encrypt.hospital.controller;

import com.encrypt.hospital.model.Appointment;
import com.encrypt.hospital.model.Doctor;
import com.encrypt.hospital.model.Patient;
import com.encrypt.hospital.service.AppointmentService;
import com.encrypt.hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;

    // 获取指定科室的医生信息
    @GetMapping("/doctors/{subDepartment}")
    public List<Doctor> getDoctorsByDepartment(@PathVariable String subDepartment) {
        return doctorService.findDoctorsByDepartment(subDepartment);
    }

    // 创建预约
    @PostMapping("/create")
    public ResponseEntity<?> createAppointment(@RequestBody Map<String,String> payload) {
        try {
            int docID = Integer.parseInt(payload.get("DocID"));
            int patientID = Integer.parseInt(payload.get("PatientId"));
            String time = payload.get("Time");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // 尝试解析字符串为日期
            Date date = sdf.parse(payload.get("Date"));
            Appointment savedAppointment = new Appointment();
            savedAppointment.setDocID(docID);
            savedAppointment.setPatientID(patientID);
            savedAppointment.setTime(time);
            savedAppointment.setDate(date);
            savedAppointment.setDepartment(payload.get("Outpatient"));
            savedAppointment.setState(payload.get("State"));
            appointmentService.createAppointment(savedAppointment);
            return ResponseEntity.ok(savedAppointment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("bad");
        }
    }
}