package com.encrypt.hospital.service;

import com.encrypt.hospital.model.Doctor;
import com.encrypt.hospital.repository.DoctorRepository;
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

    public Doctor getDoctorDetails(int userID) {
        return doctorRepository.findByUserID(userID);
    }

    @Transactional
    public void updateDoctorDetails(Doctor updatedDoctor) {
        Doctor doctor = doctorRepository.findByUserID(updatedDoctor.getUserID());
        if (doctor != null) {
            doctor.setDocName(updatedDoctor.getDocName());
            doctor.setDepartment(updatedDoctor.getDepartment());
            doctor.setTitle(updatedDoctor.getTitle());
            doctor.setEmail(updatedDoctor.getEmail());
            doctor.setPhoneNumber(updatedDoctor.getPhoneNumber());
            doctor.setProfile(updatedDoctor.getProfile());
            doctorRepository.save(doctor);
        } else {
            throw new IllegalStateException("No doctor found with the user ID: " + updatedDoctor.getUserID());
        }
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
