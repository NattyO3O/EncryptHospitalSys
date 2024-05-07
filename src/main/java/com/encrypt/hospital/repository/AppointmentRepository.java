package com.encrypt.hospital.repository;

import com.encrypt.hospital.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
}