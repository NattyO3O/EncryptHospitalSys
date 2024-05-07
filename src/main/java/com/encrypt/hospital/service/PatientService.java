package com.encrypt.hospital.service;

import com.encrypt.hospital.model.Patient;
import com.encrypt.hospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient getPatientDetails(int userID) {
        return patientRepository.findByUserID(userID);
    }



    @Transactional
    public void updatePatientDetails(Patient updatedPatient) {
        Patient patient = patientRepository.findByUserID(updatedPatient.getUserID());
        if (patient != null) {
            patient.setPatientName(updatedPatient.getPatientName());
            patient.setSex(updatedPatient.getSex());
            patient.setAge(updatedPatient.getAge());
            patient.setPhoneNumber(updatedPatient.getPhoneNumber());
            patient.setNationalID(updatedPatient.getNationalID());
            patientRepository.save(patient);
        } else {
            throw new IllegalStateException("No patient found with the user ID: " + updatedPatient.getUserID());
        }
    }

}
