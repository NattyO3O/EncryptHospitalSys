package com.encrypt.hospital.service;

import com.encrypt.hospital.model.Doctor;
import com.encrypt.hospital.model.HpUser;
import com.encrypt.hospital.repository.DoctorRepository;
import com.encrypt.hospital.repository.HpUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private HpUserRepository userRepository;

    public Doctor getDoctorDetails(int userId) {
        return doctorRepository.findByUserId(userId);
    }

    @Transactional
    public void updateDoctorDetails(Doctor updatedDoctor) {
        Doctor doctor = doctorRepository.findByUserId(updatedDoctor.getUserId());
        if (doctor != null) {
            setUpDateDoc(updatedDoctor, doctor);
        } else {
            throw new IllegalStateException("No doctor found with the user ID: " + updatedDoctor.getUserId());
        }
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Transactional
    public void deleteDoctorById(int doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new IllegalStateException("No doctor found with the ID: " + doctorId));
        int userId = doctor.getUserId();
        doctorRepository.delete(doctor);
        HpUser user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("No user found with the ID: " + userId));

        userRepository.delete(user);
    }

    public void addDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
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

        doctorRepository.save(existingDoctor);
    }
}
