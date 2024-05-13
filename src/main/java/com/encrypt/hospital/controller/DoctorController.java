package com.encrypt.hospital.controller;

import com.encrypt.hospital.model.Doctor;
import com.encrypt.hospital.model.HpUser;
import com.encrypt.hospital.service.DoctorService;
import com.encrypt.hospital.service.EncryptionService;
import com.encrypt.hospital.service.HpUserService;
import com.encrypt.hospital.util.HMACSM3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private HpUserService hpUserService;

    @GetMapping("/details/{userID}")
    public ResponseEntity<Doctor> getDoctorDetails(@PathVariable int userID) {
        return ResponseEntity.ok(doctorService.getDoctorDetails(userID));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateDoctorDetails(@RequestBody Map<String, Object> payload) {
        try {
            int userID = (int) payload.get("userId");
            Map<String, String> values = (Map<String, String>) payload.get("values");
            Doctor doctor = new Doctor();
            doctor.setUserID(userID);
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

    @GetMapping("/all")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addDoctor(@RequestBody Map<String, Object> payload) {
        Map<String, String> userData = (Map<String, String>) payload.get("userData");
        try {
            HpUser user = new HpUser();
            // 获取用户名和密码
            String username = userData.get("username");
            String password = userData.get("password");
            // 加密密码
            byte[] encryptedPassword = EncryptionService.encryptSM4(password);
            String base64EncodedPassword = Base64.getEncoder().encodeToString(encryptedPassword);
            user.setUserName(username);
            user.setPassWord(base64EncodedPassword);
            user.setType("Doctor");
            HpUser savedUser = hpUserService.save(user);

            //用户表中的完整性
            savedUser.setUserID_MAC(HMACSM3.generateHmacSm3(String.valueOf(savedUser.getUserID())));
            savedUser.setUserName_MAC(HMACSM3.generateHmacSm3(savedUser.getUserName()));
            savedUser.setPassWord_MAC(HMACSM3.generateHmacSm3(savedUser.getPassWord()));
            savedUser.setType_MAC(HMACSM3.generateHmacSm3(savedUser.getType()));
            hpUserService.save(savedUser);

            Doctor doctor = new Doctor();
            doctor.setUserID(savedUser.getUserID());
            doctor.setDocName(savedUser.getUserName());
            doctor.setDepartment("null");
            doctor.setEmail("null");
            doctor.setPhoneNumber("null");
            doctor.setProfile("null");
            doctor.setTitle("null");
            doctorService.addDoctor(doctor);

            return ResponseEntity.ok("医生添加成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("添加失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{doctorId}")
    public ResponseEntity<?> deleteDoctor(@PathVariable int doctorId) {
        try {
            //int userId = doctorService.findUserIdByDoctorId(doctorId);
            doctorService.deleteDoctorById(doctorId);
            //hpUserService.deleteUserById(userId);
            return ResponseEntity.ok("医生删除成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除失败: " + e.getMessage());
        }
    }

    // Update doctor details using docID
    @PutMapping("/update/{doctorId}")
    public ResponseEntity<?> updateDoctorDetails(@PathVariable int doctorId, @RequestBody Map<String, String> values) {
        try {
            Doctor doctor = new Doctor();
            doctor.setDocID(doctorId);
            doctor.setDocName(values.get("docName"));
            doctor.setDepartment(values.get("department"));
            doctor.setTitle(values.get("title"));
            doctor.setEmail(values.get("email"));
            doctor.setPhoneNumber(values.get("phoneNumber"));
            doctor.setProfile(values.get("profile"));

            doctorService.updateDoctorByDocID(doctor);
            return ResponseEntity.ok("个人信息修改成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新失败: " + e.getMessage());
        }
    }
}