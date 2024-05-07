package com.encrypt.hospital.repository;

import com.encrypt.hospital.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Doctor findByUserID(int userID);
    List<Doctor> findByDepartment(String department);
}