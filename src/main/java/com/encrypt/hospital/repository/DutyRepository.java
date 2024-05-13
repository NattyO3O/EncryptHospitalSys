package com.encrypt.hospital.repository;

import com.encrypt.hospital.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DutyRepository extends JpaRepository<Appointment, Integer> {
    @Query(value = "SELECT DATE(a.date) as date, " +
            "SUM(CASE WHEN a.time = '上午' THEN 1 ELSE 0 END) as morningCount, " +
            "SUM(CASE WHEN a.time = '下午' THEN 1 ELSE 0 END) as afternoonCount " +
            "FROM Appointment a WHERE a.docID = :docID " +
            "GROUP BY DATE(a.date)", nativeQuery = true)
    List<AppointmentCountDto> findAppointmentCountsByDoctor(@Param("docID") int docID);
}
