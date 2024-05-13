package com.encrypt.hospital.service;

import com.encrypt.hospital.model.Doctor;
import com.encrypt.hospital.model.HpUser;
import com.encrypt.hospital.repository.DoctorRepository;
import com.encrypt.hospital.repository.HpUserRepository;
import com.encrypt.hospital.util.HMACSM3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private HpUserRepository userRepository;

    public Doctor getDoctorDetails(int userID) {
        return doctorRepository.findByUserID(userID);
    }

    @Transactional
    public void updateDoctorDetails(Doctor updatedDoctor) {
        Doctor doctor = doctorRepository.findByUserID(updatedDoctor.getUserID());
        if (doctor != null) {
            setUpDateDoc(updatedDoctor, doctor);
        } else {
            throw new IllegalStateException("No doctor found with the user ID: " + updatedDoctor.getUserID());
        }
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Transactional
    public void deleteDoctorById(int doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalStateException("No doctor found with the ID: " + doctorId));
        int userID = doctor.getUserID();
        doctorRepository.delete(doctor);
        HpUser user = userRepository.findById(userID)
                .orElseThrow(() -> new IllegalStateException("No user found with the ID: " + userID));

        userRepository.delete(user);
    }

    public void addDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
        //完整性
        genHMACforDoc(doctor, doctorRepository);
    }

    public void updateDoctorByDocID(Doctor updatedDoctor) {
        Doctor existingDoctor = doctorRepository.findById(updatedDoctor.getDocID())
                .orElseThrow(() -> new IllegalStateException("No doctor found with the ID: " + updatedDoctor.getDocID()));

        setUpDateDoc(updatedDoctor, existingDoctor);
    }

    private void setUpDateDoc(Doctor updatedDoctor, Doctor existingDoctor) {
        existingDoctor.setDocName(updatedDoctor.getDocName());
        existingDoctor.setDepartment(updatedDoctor.getDepartment());
        existingDoctor.setTitle(updatedDoctor.getTitle());
        existingDoctor.setEmail(updatedDoctor.getEmail());
        existingDoctor.setPhoneNumber(updatedDoctor.getPhoneNumber());
        existingDoctor.setProfile(updatedDoctor.getProfile());

        //完整性
        genHMACforDoc(existingDoctor, doctorRepository);
    }

    static void genHMACforDoc(Doctor existingDoctor, DoctorRepository doctorRepository) {
        existingDoctor.setDocID_MAC(HMACSM3.generateHmacSm3(String.valueOf(existingDoctor.getDocID())));
        existingDoctor.setUserID_MAC(HMACSM3.generateHmacSm3(String.valueOf(existingDoctor.getUserID())));
        existingDoctor.setDocName_MAC(HMACSM3.generateHmacSm3(existingDoctor.getDocName()));
        existingDoctor.setDepartment_MAC(HMACSM3.generateHmacSm3(existingDoctor.getDepartment()));
        existingDoctor.setTitle_MAC(HMACSM3.generateHmacSm3(existingDoctor.getTitle()));
        existingDoctor.setEmail_MAC(HMACSM3.generateHmacSm3(existingDoctor.getEmail()));
        existingDoctor.setPhoneNumber_MAC(HMACSM3.generateHmacSm3(existingDoctor.getPhoneNumber()));
        existingDoctor.setProfile_MAC(HMACSM3.generateHmacSm3(existingDoctor.getProfile()));
        doctorRepository.save(existingDoctor);
    }

    private static final Map<String, String> subToMainDepartment = new HashMap<>();
    static {
        subToMainDepartment.put("心脏内科", "内科");
        subToMainDepartment.put("呼吸病", "内科");
        subToMainDepartment.put("普通内科", "内科");
        subToMainDepartment.put("口腔科", "外科");
        subToMainDepartment.put("皮肤科", "外科");
        subToMainDepartment.put("眼科", "外科");
        subToMainDepartment.put("中医呼吸科", "中医");
        subToMainDepartment.put("针灸科", "中医");
        subToMainDepartment.put("推拿科", "中医");
        subToMainDepartment.put("小儿内分泌", "儿科");
        subToMainDepartment.put("儿童眼科", "儿科");
    }

    public List<Doctor> findDoctorsByDepartment(String subDepartment) {
        String mainDepartment = subToMainDepartment.getOrDefault(subDepartment, "未知科室");
        return doctorRepository.findByDepartment(mainDepartment);
    }
}
