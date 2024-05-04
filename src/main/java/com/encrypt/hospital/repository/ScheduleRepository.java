package com.encrypt.hospital.repository;

import com.encrypt.hospital.model.DutySchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<DutySchedule, Integer> {
    List<DutySchedule> findByDocIDAndDate(Integer docID, Date date);
}
