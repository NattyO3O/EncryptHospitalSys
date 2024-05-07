package com.encrypt.hospital.repository;

import com.encrypt.hospital.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Patient findByUserID(int userID);
}
