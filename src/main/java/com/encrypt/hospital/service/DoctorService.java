package com.encrypt.hospital.service;

import com.encrypt.hospital.model.Doctor;
import com.encrypt.hospital.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor getDoctorDetails(int userId) {
        return doctorRepository.findByUserId(userId);
    }

    @Transactional
    public void updateDoctorDetails(Doctor updatedDoctor) {
        Doctor doctor = doctorRepository.findByUserId(updatedDoctor.getUserId());
        if (doctor != null) {
            doctor.setDocName(updatedDoctor.getDocName());
            doctor.setDepartment(updatedDoctor.getDepartment());
            doctor.setTitle(updatedDoctor.getTitle());
            doctor.setEmail(updatedDoctor.getEmail());
            doctor.setPhoneNumber(updatedDoctor.getPhoneNumber());
            doctor.setProfile(updatedDoctor.getProfile());
            doctorRepository.save(doctor);
        } else {
            throw new IllegalStateException("No doctor found with the user ID: " + updatedDoctor.getUserId());
        }
    }
}
