package com.encrypt.hospital.service;

import com.encrypt.hospital.model.DutySchedule;
import com.encrypt.hospital.model.DutySchedule;
import com.encrypt.hospital.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository repository;

    public List<DutySchedule> findAll() {
        return repository.findAll();
    }

    public List<DutySchedule> findByDoctorAndDate(Integer docID, Date date) {
        return repository.findByDocIDAndDate(docID, date);
    }

    public DutySchedule save(DutySchedule schedule) {
        return repository.save(schedule);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
